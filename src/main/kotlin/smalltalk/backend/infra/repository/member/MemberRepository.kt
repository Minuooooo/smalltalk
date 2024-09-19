package smalltalk.backend.infra.repository.member

import smalltalk.backend.domain.member.Member
import smalltalk.backend.exception.member.situation.MemberNotFoundException

fun MemberRepository.getById(sessionId: String) = findById(sessionId) ?: throw MemberNotFoundException()

interface MemberRepository {
    fun save(sessionId: String, id: Long, roomId: Long): Member
    fun findById(sessionId: String): Member?
    fun deleteById(sessionId: String)
    fun deleteAll()
}