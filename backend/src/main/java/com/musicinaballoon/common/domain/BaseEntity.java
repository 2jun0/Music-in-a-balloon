package com.musicinaballoon.common.domain;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import lombok.Getter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {

    @Column(name = "created_at", updatable = false, nullable = false)
    private ZonedDateTime created_at;

    @Column(name = "updated_at", nullable = false)
    private ZonedDateTime updated_at;

    @PrePersist
    public void prePersist() {
        ZonedDateTime now = ZonedDateTime.now(ZoneOffset.UTC);
        created_at = now;
        updated_at = now;
    }

    @PreUpdate
    public void preUpdate() {
        ZonedDateTime.now(ZoneOffset.UTC);
    }
}