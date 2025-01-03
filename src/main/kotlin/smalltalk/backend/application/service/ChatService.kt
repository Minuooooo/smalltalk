package smalltalk.backend.application.service

import org.springframework.stereotype.Service
import smalltalk.backend.config.websocket.WebSocketConfig
import smalltalk.backend.presentation.dto.message.Chat
import smalltalk.backend.application.MessageBroker

@Service
class ChatService(
    private val broker: MessageBroker,
) {

    fun send(id: String, message: Chat) {
        broker.send(WebSocketConfig.SUBSCRIBE_ROOM_DESTINATION_PREFIX + id, message)
    }
}