package com.finor.invoice.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "gl_accounts")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class GlAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String glCode;   // e.g. 6400

    @Column(nullable = false)
    private String glName;   // e.g. Cloud Services Expense

    private String category; // e.g. Cloud / Travel / Supplies

    @Column(nullable = false)
    private Boolean active = true;

}
