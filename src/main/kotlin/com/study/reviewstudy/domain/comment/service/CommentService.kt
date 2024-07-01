package com.study.reviewstudy.domain.comment.service

import com.study.reviewstudy.common.exception.handler.ModelNotFoundException
import com.study.reviewstudy.domain.comment.dto.CommentCreateRequest
import com.study.reviewstudy.domain.comment.dto.CommentIdResponse
import com.study.reviewstudy.domain.comment.dto.CommentUpdateRequest
import com.study.reviewstudy.domain.comment.model.Comment
import com.study.reviewstudy.domain.comment.repository.CommentRepository
import com.study.reviewstudy.domain.member.repository.MemberRepository
import com.study.reviewstudy.domain.post.repository.PostRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class CommentService(
    private val commentRepository: CommentRepository,
    private val memberRepository: MemberRepository,
    private val postRepository: PostRepository,
) {
    fun createComment(postId: Long, request: CommentCreateRequest, nickname: String): CommentIdResponse {
        val post = postRepository.findPostByIdAndDeletedAtIsNull(postId).orElseThrow() {
            IllegalStateException("Post not found")
        }
        val member = memberRepository.findByNickname(nickname) ?: throw ModelNotFoundException("Member", nickname)
        val comment = Comment.createComment(request, member, post)

        return commentRepository.save(comment).toIdResponse()
    }

    fun updateComment(id: Long, request: CommentUpdateRequest, nickname: String) {
        val member = memberRepository.findByNickname(nickname) ?: throw ModelNotFoundException("Member", nickname)
        val comment = commentRepository.findById(id).orElseThrow() {
            IllegalStateException("Comment not found")
        }
        if (comment.member != member) {
            throw IllegalStateException("author not same")
        }
        comment.update(request)
    }

    fun deleteComment(id: Long, nickname: String) {
        val member = memberRepository.findByNickname(nickname) ?: throw ModelNotFoundException("Member", nickname)
        val comment = commentRepository.findById(id).orElseThrow() {
            IllegalStateException("Comment not found")
        }
        if (comment.member != member) {
            throw IllegalStateException("author not same")
        }
        commentRepository.delete(comment)
    }
}