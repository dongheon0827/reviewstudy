package com.study.reviewstudy.domain.member.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size

data class MemberCreateRequest(
    @field:NotBlank(message = "email cannot be blank")
    @field:Size(min = 3, message = "Nickname must be at least 3 characters long")
    @field:Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)[a-zA-Z\\d]{3,}\$")
    val nickname: String,

    @field:NotBlank(message = "password cannot be blank")
    @field:Size(min = 4)
    val password: String,

    @field:NotBlank(message = "confirm password cannot be blank")
    val confirmPassword: String,
)