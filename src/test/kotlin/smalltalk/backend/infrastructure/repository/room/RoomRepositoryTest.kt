package smalltalk.backend.infrastructure.repository.room

import io.github.oshai.kotlinlogging.KotlinLogging
import io.kotest.core.spec.style.ExpectSpec
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ActiveProfiles
import smalltalk.backend.support.redis.RedisContainerConfig
import smalltalk.backend.support.redis.RedisTestConfig

@ActiveProfiles("test")
@SpringBootTest(classes = [RoomRepository::class, RedisRoomRepository::class, RedisTestConfig::class, RedisContainerConfig::class])
@DirtiesContext
internal class RoomRepositoryTest (
    private val roomRepository: RoomRepository
): ExpectSpec({
    val logger = KotlinLogging.logger {  }

    beforeEach {
        roomRepository.save("안녕하세요~")
        roomRepository.save("반가워요!")
        roomRepository.save("siuuuuu")
    }

    context("채팅방을 저장할 경우") {
        val roomName = "Team small talk"
        expect("입력 받은 채팅방 이름을 통해 저장된 채팅방의 id를 반환한다") {
            val savedRoomId = roomRepository.save(roomName)
            savedRoomId shouldBe 4L
        }
    }

    context("채팅방을 채팅방 id로 조회할 경우") {
        expect("id가 1이면 일치하는 채팅방을 반환한다") {
            val firstFoundRoom = roomRepository.findById(1L)
            firstFoundRoom?.id shouldBe 1L
            firstFoundRoom?.name shouldBe "안녕하세요~"
        }
        expect("id가 일치하는 채팅방이 없으면 null 값을 반환한다") {
            val foundRoom = roomRepository.findById(4L)
            foundRoom.shouldBeNull()
        }
    }

    context("모든 채팅방을 조회할 경우") {
        expect("채팅방이 1개 이상 존재하면 모든 채팅방을 반환한다") {
            roomRepository.findAll().size shouldBe 3
        }
    }

    afterEach {
        roomRepository.deleteAll()
    }
})