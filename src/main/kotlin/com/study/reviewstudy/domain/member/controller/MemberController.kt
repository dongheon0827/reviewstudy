package com.study.reviewstudy.domain.member.controller

import com.study.reviewstudy.domain.member.dto.LoginRequest
import com.study.reviewstudy.domain.member.dto.LoginTokenResponse
import com.study.reviewstudy.domain.member.dto.MemberCreateRequest
import com.study.reviewstudy.domain.member.service.MemberService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/members")
class MemberController(
    private val memberService: MemberService
) {
    @PostMapping("/signup")
    fun signupMember(@RequestBody @Valid memberCreateRequest: MemberCreateRequest): ResponseEntity<Unit> {
        return ResponseEntity.status(HttpStatus.CREATED).body(memberService.signup(memberCreateRequest))
    }

    @PostMapping
    fun login(@RequestBody @Valid loginRequest: LoginRequest): ResponseEntity<LoginTokenResponse> {
        return ResponseEntity.status(HttpStatus.OK).body(memberService.login(loginRequest))
    }

    @DeleteMapping
    fun deleteMember(): ResponseEntity<Unit> {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(memberService.deleteMember("nickname"))
    }
}