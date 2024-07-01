package com.study.reviewstudy.domain.post.controller

import com.study.reviewstudy.domain.post.dto.PostCreateRequest
import com.study.reviewstudy.domain.post.dto.PostIdResponse
import com.study.reviewstudy.domain.post.dto.PostUpdateRequest
import com.study.reviewstudy.domain.post.dto.PostWithCommentResponse
import com.study.reviewstudy.domain.post.service.PostService
import com.study.reviewstudy.infra.security.UserPrincipal
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/posts")
class PostController(
    private val postService: PostService
) {
    @GetMapping("/{postId}")
    fun getPostById(
        @AuthenticationPrincipal userPrincipal: UserPrincipal?,
        @PathVariable postId: Long
    ): ResponseEntity<PostWithCommentResponse> {
        return ResponseEntity.status(HttpStatus.OK).body(postService.getPostById(postId, userPrincipal!!.nickname))
    }

    @PostMapping
    fun createPost(
        @AuthenticationPrincipal userPrincipal: UserPrincipal?,
        @RequestBody postCreateRequest: PostCreateRequest
    ): ResponseEntity<PostIdResponse> {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(postService.createPost(postCreateRequest, userPrincipal!!.nickname))
    }

    @PutMapping("/{postId}")
    fun updatePost(
        @AuthenticationPrincipal userPrincipal: UserPrincipal?,
        @PathVariable("postId") id: Long, request: PostUpdateRequest
    ): ResponseEntity<Unit> {
        return ResponseEntity.status(HttpStatus.OK).body(postService.updatePost(id, request, userPrincipal!!.nickname))
    }

    @DeleteMapping("/{postId}")
    fun deletePost(
        @AuthenticationPrincipal userPrincipal: UserPrincipal?,
        @PathVariable("postId") id: Long
    ):ResponseEntity<Unit>{
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(postService.deletePost(id, userPrincipal!!.nickname))
    }
}