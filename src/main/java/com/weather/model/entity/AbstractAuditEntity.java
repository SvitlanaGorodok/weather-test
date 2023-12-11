package com.weather.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractAuditEntity {

  @Version
  @Column(name = "version")
  private int version;

  @CreatedDate
  @Column(name = "created", nullable = false, updatable = false)
  private LocalDateTime created;

  @LastModifiedDate
  @Column(name = "updated", nullable = false)
  private LocalDateTime updated;
}
