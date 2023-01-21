package com.soon83.domain

import jakarta.persistence.MappedSuperclass
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.LastModifiedBy
import java.time.ZonedDateTime

@MappedSuperclass
//@EntityListeners(AuditingEntityListener::class)
abstract class BaseEntity {

    @CreationTimestamp
    var createdAt: ZonedDateTime? = null

    @UpdateTimestamp
    var updatedAt: ZonedDateTime? = null

    @CreatedBy
    var createdBy: String? = null

    @LastModifiedBy
    var modifiedBy: String? = null
}