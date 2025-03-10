package smalltalk.backend.repository

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.test.context.SpringBootTest
import smalltalk.backend.MEMBER_LIMIT
import smalltalk.backend.NAME
import smalltalk.backend.config.property.RoomYamlProperties
import smalltalk.backend.config.redis.RedisConfig
import smalltalk.backend.domain.room.RedissonRoomRepository
import smalltalk.backend.domain.room.RoomRedisFunctionsLoader
import smalltalk.backend.domain.room.RoomRepository
import smalltalk.backend.domain.room.getById
import smalltalk.backend.util.ObjectMapperClient
import smalltalk.backend.support.EnableTestContainer
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors

@SpringBootTest(
    classes = [RedisConfig::class, RoomRedisFunctionsLoader::class, RedissonRoomRepository::class, ObjectMapperClient::class]
)
@EnableConfigurationProperties(value = [RoomYamlProperties::class])
@EnableTestContainer
class RoomRepositoryConcurrencyTest(
    private val roomRepository: RoomRepository,
) : FunSpec({

    test("채팅방에 멤버를 동시에 추가하면 가득차야 한다") {
        val numberOfThread = MEMBER_LIMIT - 1
        val threadPool = Executors.newFixedThreadPool(numberOfThread)
        val latch = CountDownLatch(numberOfThread)
        val id = roomRepository.save(NAME).id

        repeat(numberOfThread) {
            threadPool.submit {
                try {
                    roomRepository.addMember(id)
                }
                finally {
                    latch.countDown()
                }
            }
        }

        latch.await()
        roomRepository.getById(id).numberOfMember shouldBe MEMBER_LIMIT
    }

    test("가득찬 채팅방에서 동시에 모든 멤버를 삭제하면 채팅방이 삭제되어야 한다") {
        val numberOfThread = MEMBER_LIMIT
        val threadPool = Executors.newFixedThreadPool(numberOfThread)
        val latch = CountDownLatch(numberOfThread)
        val id = roomRepository.save(NAME).id
        repeat(numberOfThread - 1) { roomRepository.addMember(id) }

        repeat(numberOfThread) {
            threadPool.submit {
                try {
                    roomRepository.deleteMember(id, (it + 1).toLong())
                }
                finally {
                    latch.countDown()
                }
            }
        }

        latch.await()
        roomRepository.findById(id).shouldBeNull()
    }

    afterTest {
        roomRepository.deleteAll()
    }
})