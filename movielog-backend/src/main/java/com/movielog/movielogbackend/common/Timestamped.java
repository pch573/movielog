package com.movielog.movielogbackend.common;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass // 이 클래스를 상속하는 엔티티는 아래 필드들을 컬럼으로 인식하게 됩니다.
@EntityListeners(AuditingEntityListener.class) // 생성/ 수정 시간을 자동으로 감지하여 업데이트합니다.
public abstract class Timestamped {

    @CreatedDate
    @Column(updatable = false) // 생성 시간은 수정되지 않도록 설정
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}
