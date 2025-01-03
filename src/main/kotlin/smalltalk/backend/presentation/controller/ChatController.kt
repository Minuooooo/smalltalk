package smalltalk.backend.presentation.controller

import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.stereotype.Controller
import smalltalk.backend.application.service.ChatService
import smalltalk.backend.presentation.dto.message.Chat

@Controller
class ChatController(
    private val chatService: ChatService,
) {

    @MessageMapping("{id}")
    fun send(@DestinationVariable("id") id: String, message: Chat) {
        chatService.send(id, message)
    }
}