package com.study.reviewstudy.domain.member.service

import com.study.reviewstudy.common.exception.handler.DuplicateArgumentException
import com.study.reviewstudy.common.exception.handler.InvalidCredentialException
import com.study.reviewstudy.common.exception.handler.ModelNotFoundException
import com.study.reviewstudy.domain.member.dto.LoginRequest
import com.study.reviewstudy.domain.member.dto.LoginTokenResponse
import com.study.reviewstudy.domain.member.dto.MemberCreateRequest
import com.study.reviewstudy.domain.member.model.Member
import com.study.reviewstudy.domain.member.model.Role
import com.study.reviewstudy.domain.member.repository.MemberRepository
import com.study.reviewstudy.infra.security.jwt.JwtPlugin
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class MemberService(
    private val memberRepository: MemberRepository,
    private val bCryptPasswordEncoder: BCryptPasswordEncoder,
    private val jwtPlugin: JwtPlugin,
//    private val jwtPlugin: JwtPlugin
) {
    fun signup(memberCreateRequest: MemberCreateRequest) {
        if (memberRepository.existsByNickname(memberCreateRequest.nickname)) {
            throw DuplicateArgumentException("Member", memberCreateRequest.nickname)
        }
        val member = Member()
        member.let {
            it.role = Role.USER
            it.nickname = memberCreateRequest.nickname
            it.password = bCryptPasswordEncoder.encode(memberCreateRequest.password)
        }
        memberRepository.save(member)
    }

    fun deleteMember(memberNickname: String) {
        val member =
            memberRepository.findByNickname(memberNickname) ?: throw ModelNotFoundException("Member", memberNickname)

        memberRepository.delete(member)
    }

    fun login(loginRequest: LoginRequest): LoginTokenResponse {
        val member = memberRepository.findByNickname(loginRequest.nickname) ?: throw ModelNotFoundException(
            "Member",
            loginRequest.nickname
        )
        if (!bCryptPasswordEncoder.matches(loginRequest.password, member.password)) {
            throw InvalidCredentialException()
        }

        return LoginTokenResponse(
            accessToken = jwtPlugin.generateAccessToken(
                subject = member.id.toString(),
                nickname = member.nickname!!,
                role = member.role
            )
        )
    }
}