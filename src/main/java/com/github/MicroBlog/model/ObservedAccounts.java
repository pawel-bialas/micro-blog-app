package com.github.MicroBlog.model;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table (name = "Observe")
public class ObservedAccounts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long observedAccId;
    private Long observingAccId;
    @CreationTimestamp
    private LocalDateTime creationDate;
}
