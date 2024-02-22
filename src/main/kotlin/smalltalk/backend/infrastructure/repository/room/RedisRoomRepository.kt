package smalltalk.backend.infrastructure.repository.room

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Repository
import smalltalk.backend.application.exception.room.situation.RoomIdNotFoundException
import smalltalk.backend.domain.room.Room

@Repository
class RedisRoomRepository(
    private val redisTemplate: RedisTemplate<String, String>,
    private val objectMapper: ObjectMapper
) : RoomRepository {
    companion object {
        private const val ROOM_LIMIT_MEMBER_COUNT = 10
        private const val ROOM_COUNTER_KEY = "roomCounter"
        private const val ROOM_KEY = "room:"
    }

    override fun save(roomName: String): Room {
        val generatedRoomId = generateRoomId()
        val room =
            Room(
                generatedRoomId,
                roomName,
                (2..ROOM_LIMIT_MEMBER_COUNT).toMutableList(),
                mutableListOf(1)
            )
        redisTemplate.opsForValue()[ROOM_KEY + generatedRoomId] = convertTypeToString(room)
        return room
    }

    override fun findById(roomId: Long): Room? = findByKey(ROOM_KEY + roomId)

    override fun findAll() =
        findKeysByPattern("$ROOM_KEY*").mapNotNull {
            findByKey(it)
        }

    override fun deleteById(roomId: Long) {
        redisTemplate.delete(ROOM_KEY + roomId)
    }

    override fun addMember(room: Room) =
        room.apply {
            members.add(idQueue.removeFirst())
        }

    override fun deleteMember(room: Room, memberId: Int) =
        room.apply {
            members.remove(memberId)
            idQueue.add(memberId)
        }

    override fun update(updatedRoom: Room) {
        redisTemplate.opsForValue()[ROOM_KEY + updatedRoom.id] = convertTypeToString(updatedRoom)
    }

    override fun deleteAll() {
        redisTemplate.run {
            delete(ROOM_COUNTER_KEY)
            delete(findKeysByPattern("$ROOM_KEY*"))
        }
    }

    private fun generateRoomId() =
        redisTemplate.opsForValue().increment(ROOM_COUNTER_KEY)?: throw RoomIdNotFoundException()

    private fun findByKey(key: String) =
        redisTemplate.opsForValue()[key]?.let {
            objectMapper.readValue(it, Room::class.java)
        }

    private fun findKeysByPattern(key: String) = redisTemplate.keys(key)

    private fun convertTypeToString(room: Room) = objectMapper.writeValueAsString(room)
}