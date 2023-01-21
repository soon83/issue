package com.soon83.domain

import jakarta.persistence.*

@Entity
@Table
class Comment(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column
    val userId: Long,

    @Column
    val userName: String,

    @Column
    var body: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "issue_id")
    val issue: Issue,

    ) : BaseEntity()