package com.soon83.domain.entity

import com.soon83.domain.BaseEntity
import jakarta.persistence.*

@Entity
@Table
class Issue(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column
    var userId: Long,

    @Column
    var summary: String,

    @Column
    var description: String,

    @Column
    @Enumerated(EnumType.STRING)
    var type: IssueType,

    @Column
    @Enumerated(EnumType.STRING)
    var priority: IssuePriority,

    @Column
    @Enumerated(EnumType.STRING)
    var status: IssueStatus,

    @OneToMany
    val comments: MutableList<Comment> = mutableListOf(),

    ) : BaseEntity() {

    enum class IssueType {
        BUG, TASK;

        companion object {
            operator fun invoke(type: String) = valueOf(type.uppercase())
        }
    }

    enum class IssuePriority {
        LOW, MEDIUM, HIGH;

        companion object {
            operator fun invoke(priority: String) = valueOf(priority.uppercase())
        }
    }

    enum class IssueStatus {
        TODO, IN_PROGRESS, RESOLVED;

        companion object {
            operator fun invoke(status: String) = valueOf(status.uppercase())
        }
    }
}