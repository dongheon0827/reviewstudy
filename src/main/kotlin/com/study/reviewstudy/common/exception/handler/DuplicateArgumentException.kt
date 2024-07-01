package com.study.reviewstudy.common.exception.handler

data class DuplicateArgumentException(val modelName: String, val input: String) : RuntimeException(
    "Already Existed $modelName with input $input"
)
