package com.study.reviewstudy.common.exception.handler

data class ModelNotFoundException(val modelName: String, val input: String) : RuntimeException(
    "Model $modelName not found with given input $input"
)