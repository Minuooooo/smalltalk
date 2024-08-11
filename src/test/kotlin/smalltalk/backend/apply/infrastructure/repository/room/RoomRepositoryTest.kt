package smalltalk.backend.apply.infrastructure.repository.room

import io.github.oshai.kotlinlogging.KotlinLogging
import io.kotest.core.spec.style.ExpectSpec
import io.kotest.matchers.ints.shouldBeZero
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ActiveProfiles
import smalltalk.backend.apply.NAME
import smalltalk.backend.infrastructure.repository.room.RedisRoomRepository
import smalltalk.backend.infrastructure.repository.room.RoomRepository
import smalltalk.backend.support.redis.RedisContainerConfig
import smalltalk.backend.support.redis.RedisTestConfig

@ActiveProfiles("test")
@SpringBootTest(
    classes = [RoomRepository::class, RedisRoomRepository::class, RedisTestConfig::class, RedisContainerConfig::class]
)
@DirtiesContext
class RoomRepositoryTest(
    private val roomRepository: RoomRepository
) : ExpectSpec({
    val logger = KotlinLogging.logger { }

    context("채팅방을 저장할 경우") {
        val roomName = NAME
        expect("입력 받은 채팅방 이름을 통해 저장된 채팅방을 반환한다") {
            val savedRoom = roomRepository.save(roomName)
            savedRoom.run {
                id shouldBe 1L
                name shouldBe NAME
                idQueue.size shouldBe 9
                members.size shouldBe 1
            }
        }
    }

    context("채팅방을 id로 조회할 경우") {
        roomRepository.save(NAME)
        expect("id가 1L이면 일치하는 채팅방을 반환한다") {
            val foundRoom = roomRepository.findById(1L)
            foundRoom?.run {
                name shouldBe NAME
                idQueue.size shouldBe 9
                members.size shouldBe 1
            }
        }
        expect("id가 일치하는 채팅방이 없으면 null 값을 반환한다") {
            roomRepository.findById(1L).shouldBeNull()
        }
    }

    context("모든 채팅방을 조회할 경우") {
        repeat(3) {
            roomRepository.save("채팅방$it")
        }
        expect("채팅방이 1개 이상 존재하면 모든 채팅방을 반환한다") {
            roomRepository.findAll().size shouldBe 3
        }
        expect("채팅방이 존재하지 않는다면 비어있는 리스트를 반환한다") {
            roomRepository.findAll().size.shouldBeZero()
        }
    }

    context("id가 일치하는 채팅방을 삭제할 경우") {
        roomRepository.save(NAME)
        expect("id가 1L이면 일치하는 채팅방을 삭제한다") {
            roomRepository.run {
                deleteById(1L)
                findById(1L).shouldBeNull()
            }
        }
    }

    context("모든 채팅방을 삭제할 경우") {
        repeat(3) {
            roomRepository.save("채팅방$it")
        }
        expect("채팅방이 1개 이상 존재하면 모든 채팅방을 삭제한다") {
            roomRepository.run {
                deleteAll()
                findAll().size.shouldBeZero()
            }
        }
    }

//    context("채팅방에서 퇴장할 경우") {
//        val foundRoom =
//            roomRepository.run {
//                update(
//                    Room(
//                        1L,
//                        "miuuuuu",
//                        (2L..10L).toMutableList(),
//                        mutableListOf(1L)
//                    )
//                )
//                findById(1L)
//            }
//        expect("퇴장한 채팅방을 반환한다") {
//            val updatedRoom =
//                foundRoom?.let {
//                    roomRepository.deleteMember(it, 1L)
//                }
//            updatedRoom?.run {
//                id shouldBe 1L
//                idQueue.size shouldBe 10
//                members.size.shouldBeZero()
//            }
//        }
//    }

    afterEach {
        roomRepository.deleteAll()
    }
})