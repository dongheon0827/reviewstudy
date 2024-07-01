package com.study.reviewstudy.domain.member.dto

data class LoginRequest(
    val nickname: String,
    val password: String,
)