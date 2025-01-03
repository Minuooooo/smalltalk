package smalltalk.backend.application.service

import org.springframework.stereotype.Service
import smalltalk.backend.domain.room.RoomRepository
import smalltalk.backend.presentation.dto.room.OpenRequest
import smalltalk.backend.presentation.dto.room.EnterResponse
import smalltalk.backend.presentation.dto.room.OpenResponse
import smalltalk.backend.presentation.dto.room.SimpleInfoResponse

@Service
class RoomService(
    private val roomRepository: RoomRepository,
) {

    fun open(request: OpenRequest) = roomRepository.save(request.name).let { OpenResponse(it.id, it.numberOfMember.toLong()) }

    fun getSimpleInfos() = roomRepository.findAll().map { SimpleInfoResponse(it.id, it.name, it.numberOfMember) }

    fun enter(id: String) = EnterResponse(roomRepository.addMember(id.toLong()))
}