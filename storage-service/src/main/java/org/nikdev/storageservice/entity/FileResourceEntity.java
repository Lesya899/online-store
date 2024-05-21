package org.nikdev.storageservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "resource")
public class FileResourceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "hash")
    private String hash;

    @Column(name = "extension")
    private String extension;

    @ManyToOne(targetEntity = FileStorageEntity.class, fetch = FetchType.EAGER)
    @JoinColumn(referencedColumnName = "storage_code", name = "storage_code")
    private FileStorageEntity storage;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
