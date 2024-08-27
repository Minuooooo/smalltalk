package smalltalk.backend.apply

import smalltalk.backend.domain.room.Room

const val ID = 1L
const val NAME = "Room"
const val ID_QUEUE_INITIAL_ID = 1L
const val ID_QUEUE_LIMIT_ID = 10L

fun createRoom(
    id: Long = ID,
    name: String = NAME
) =
    Room(
        id,
        name,
        (ID_QUEUE_INITIAL_ID..ID_QUEUE_LIMIT_ID).toMutableList(),
        mutableListOf()
    )