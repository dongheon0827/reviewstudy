package com.study.reviewstudy.domain.member.model

import com.study.reviewstudy.domain.member.dto.MemberResponse
import jakarta.persistence.*
import org.hibernate.annotations.JdbcType
import org.hibernate.dialect.PostgreSQLEnumJdbcType

@Entity
@Table(name = "Member")
class Member(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @JdbcType(PostgreSQLEnumJdbcType::class)
    var role: Role = Role.USER,

    @Column(name = "email", nullable = false, unique = true)
    var nickname: String? = null,

    @Column(name = "password", nullable = false)
    var password: String? = null,
) {
    fun toResponse(): MemberResponse {
        return MemberResponse(
            memberId = id!!,
            nickname = nickname!!,
        )
    }
}