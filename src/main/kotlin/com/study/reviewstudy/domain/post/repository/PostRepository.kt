package com.study.reviewstudy.domain.post.repository

import com.study.reviewstudy.domain.post.model.Post
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface PostRepository : JpaRepository<Post, Long> {
    fun findPostByIdAndDeletedAtIsNull(id: Long): Optional<Post>
}