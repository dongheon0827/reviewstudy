package com.study.reviewstudy.domain.comment.model

import com.study.reviewstudy.domain.comment.dto.CommentCreateRequest
import com.study.reviewstudy.domain.comment.dto.CommentIdResponse
import com.study.reviewstudy.domain.comment.dto.CommentResponse
import com.study.reviewstudy.domain.comment.dto.CommentUpdateRequest
import com.study.reviewstudy.domain.member.model.Member
import com.study.reviewstudy.domain.post.model.Post
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "comment")
class Comment(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(name = "content", nullable = false)
    var content: String,

    @Column(name = "created_at", nullable = false)
    val createdAt: LocalDateTime,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post", nullable = false)
    var post: Post,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    var member: Member,


    ) {
    companion object {
        fun createComment(
            commentCreateRequest: CommentCreateRequest,
            member: Member,
            post: Post,
        ): Comment {
            return Comment(
                member = member,
                post = post,
                content = commentCreateRequest.content,
                createdAt = LocalDateTime.now(),
            )

        }
    }

    fun update(commentUpdateRequest: CommentUpdateRequest): Comment {
        this.content = commentUpdateRequest.content
        return this
    }

    fun toResponse(): CommentResponse {
        return CommentResponse(
            id = id!!,
            author = member.nickname!!,
            content = content,
            createdAt = createdAt,
        )
    }

    fun toIdResponse(): CommentIdResponse {
        return CommentIdResponse(
            id = id!!,
        )
    }
}