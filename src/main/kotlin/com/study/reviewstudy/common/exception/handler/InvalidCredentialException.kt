package com.study.reviewstudy.common.exception.handler

data class InvalidCredentialException(override val message: String? = "The Credential is invalid") : RuntimeException()