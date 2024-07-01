package com.study.reviewstudy.domain.member.repository

import com.study.reviewstudy.domain.member.model.Member
import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository : JpaRepository<Member, Long> {
    fun findByNickname(nickname: String) : Member?
    fun existsByNickname(nickname: String): Boolean
}