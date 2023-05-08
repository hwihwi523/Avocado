package com.avocado.payment.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Consumer {
    @Id
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @Builder
    public Consumer(UUID id) {
        this.id = id;
    }
}
