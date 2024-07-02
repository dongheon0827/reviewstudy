package com.study.reviewstudy

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.EnableAspectJAutoProxy

@EnableAspectJAutoProxy
@SpringBootApplication
class ReviewstudyApplication

fun main(args: Array<String>) {
    runApplication<ReviewstudyApplication>(*args)
}
