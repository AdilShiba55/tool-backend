package kz.tool.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import kz.tool.backend.enums.Bucket;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "file_info")
public class FileInfo {
    @Id
    private UUID id;

    @Enumerated(EnumType.STRING)
    private Bucket bucket;

    @Column(updatable = false)
    private Date dtCreate;
}
