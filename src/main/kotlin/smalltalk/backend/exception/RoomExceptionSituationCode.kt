package smalltalk.backend.exception

enum class RoomExceptionSituationCode(
    val code: String,
) {

    COMMON("600"),
    DELETED("601"),
    FULL("602")
}