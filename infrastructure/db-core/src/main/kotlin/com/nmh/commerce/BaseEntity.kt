package com.nmh.commerce

import jakarta.persistence.Column
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.MappedSuperclass
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime

@MappedSuperclass
abstract class BaseEntity<ID> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open val id: ID? = null

    @CreationTimestamp
    @Column
    var createdAt: LocalDateTime? = null
        private set

    @UpdateTimestamp
    @Column
    var updatedAt: LocalDateTime? = null
        private set
}
