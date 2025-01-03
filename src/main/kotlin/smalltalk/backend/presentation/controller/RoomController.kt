package smalltalk.backend.presentation.controller

import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import smalltalk.backend.application.service.RoomService
import smalltalk.backend.presentation.dto.room.OpenRequest

@RestController
@RequestMapping("/api/rooms")
class RoomController(private val roomService: RoomService) {

    @PostMapping
    fun open(@RequestBody request: OpenRequest) = ResponseEntity.status(CREATED).body(roomService.open(request))

    @GetMapping
    fun getSimpleInfos() = ResponseEntity.ok(roomService.getSimpleInfos())

    @PostMapping("/{id}")
    fun enter(@PathVariable("id") id: String) = ResponseEntity.ok(roomService.enter(id))
}