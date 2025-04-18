package com.nmh.commerce

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime

@MappedSuperclass
abstract class BaseEntity {
    @JvmField
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @CreationTimestamp
    @Column
    var createdAt: LocalDateTime? = null
        private set

    @UpdateTimestamp
    @Column
    var updatedAt: LocalDateTime? = null
        private set
}
