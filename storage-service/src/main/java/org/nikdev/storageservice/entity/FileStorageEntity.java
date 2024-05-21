package org.nikdev.storageservice.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "file_storage")
public class FileStorageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "storage_code")
    private String storageCode;

    @Column(name = "kind_code")
    private String kindCode;

}
