package com.study.reviewstudy.domain.post.model

import com.study.reviewstudy.domain.comment.model.Comment
import com.study.reviewstudy.domain.member.model.Member
import com.study.reviewstudy.domain.post.dto.*
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "posts")
class Post(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    val member: Member,

    @Column(name = "title", nullable = false)
    var title: String,

    @Column(name = "content", nullable = false)
    var content: String,

    @Column(name = "created_at", nullable = false)
    var createdAt: LocalDateTime,

    @Column(name = "deleted_at")
    var deletedAt: LocalDateTime? = null,

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, orphanRemoval = true, cascade = [CascadeType.REMOVE])
    val comments: MutableList<Comment> = mutableListOf(),
) {
    companion object {
        fun createPost(
            postCreateRequest: PostCreateRequest,
            member: Member
        ): Post {
            return Post(
                member = member,
                title = postCreateRequest.title,
                content = postCreateRequest.content,
                createdAt = LocalDateTime.now()
            )
        }
    }

    fun update(postUpdateRequest: PostUpdateRequest): Post {
        this.title = postUpdateRequest.title
        this.content = postUpdateRequest.content
        return this
    }

    fun delete() {
        deletedAt = LocalDateTime.now()
    }

    fun toResponse(): PostResponse {
        return PostResponse(
            id = id!!,
            title = title,
            author = member.nickname!!,
            createdAt = createdAt,
            content = content,
        )
    }

    fun toIdResponse(): PostIdResponse {
        return PostIdResponse(
            id = id!!,
        )
    }

    fun toResponseWithComments(): PostWithCommentResponse {
        return PostWithCommentResponse(
            id = id!!,
            title = title,
            author = member.nickname!!,
            createdAt = createdAt,
            content = content,
            comments = comments.map { it.toResponse() },
        )
    }
}