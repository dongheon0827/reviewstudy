package com.study.reviewstudy.domain.post.service

import com.study.reviewstudy.common.exception.handler.ModelNotFoundException
import com.study.reviewstudy.domain.member.repository.MemberRepository
import com.study.reviewstudy.domain.post.dto.*
import com.study.reviewstudy.domain.post.model.Post
import com.study.reviewstudy.domain.post.repository.PostRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class PostService(
    private val postRepository: PostRepository,
    private val memberRepository: MemberRepository
) {
    fun getAllPosts(): List<PostResponse>? {
        return null
    }

    fun getPostById(id: Long, nickname: String): PostWithCommentResponse {
        val post = postRepository.findPostByIdAndDeletedAtIsNull(id).orElseThrow() {
            IllegalStateException("Post not found")
        }

        return post.toResponseWithComments()
    }

    fun createPost(request: PostCreateRequest, nickname: String): PostIdResponse {
        val member = memberRepository.findByNickname(nickname) ?: throw ModelNotFoundException("Member", nickname)
        val post = Post.createPost(request, member)

        return postRepository.save(post).toIdResponse()
    }

    fun updatePost(id: Long, request: PostUpdateRequest, nickname: String) {
        val member = memberRepository.findByNickname(nickname) ?: throw ModelNotFoundException("Member", nickname)
        val post = postRepository.findPostByIdAndDeletedAtIsNull(id).orElseThrow() {
            IllegalStateException("Post not found")
        }
        if (post.member != member) {
            throw IllegalStateException("author not same")
        }
        post.update(request)
    }

    fun deletePost(id: Long, nickname: String) {
        val member = memberRepository.findByNickname(nickname) ?: throw ModelNotFoundException("Member", nickname)
        val post = postRepository.findPostByIdAndDeletedAtIsNull(id).orElseThrow() {
            IllegalStateException("Post not found")
        }
        if (post.member != member) {
            throw IllegalStateException("author not same")
        }

        post.delete()
        postRepository.save(post)
    }
}