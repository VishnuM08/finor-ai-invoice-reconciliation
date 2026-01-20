package com.finor.invoice.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="vendor_gl_mapping")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class VendorGlMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String vendorKeyword; 
    // Example: "amazon", "aws", "uber", "zomato"

    @ManyToOne
    @JoinColumn(name="gl_account_id", nullable = false)
    private GlAccount glAccount;

    private Boolean active = true;
}
