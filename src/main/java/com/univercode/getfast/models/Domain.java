package com.univercode.getfast.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@MappedSuperclass
@Data
public class Domain {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID id;


    @Basic
    @Column(name = "creation_date")
    private LocalDateTime creationDate = LocalDateTime.now();

    @Basic
    private Boolean deleted = false;

    @Basic
    @Column(name = "last_update")
    private LocalDateTime lastUpdate = LocalDateTime.now();
}