package smalltalk.backend.application.service.room

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Service
import smalltalk.backend.application.implement.room.RoomManager
import smalltalk.backend.application.implement.room.RoomResponseMapper
import smalltalk.backend.application.implement.message.MessageBroker
import smalltalk.backend.config.websocket.WebSocketConfig
import smalltalk.backend.presentation.dto.message.Message


@Service
class RoomService (
    private val roomManager: RoomManager,
    private val roomResponseMapper: RoomResponseMapper,
    private val messageBroker: MessageBroker
) {

    private val logger = KotlinLogging.logger {}
    private val nicknamePrefix = "익명"

    fun send(roomId: String, message: Message) {
        logger.debug { "call send method in service" }
        messageBroker.send(WebSocketConfig.SEND_DESTINATION_PREFIX + roomId, message)
    }

    fun enter() {

    }

    fun open() {

    }

    fun exit() {
    }
}