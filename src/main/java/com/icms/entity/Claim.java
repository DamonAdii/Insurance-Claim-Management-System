package com.icms.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="claim")
public class Claim {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="claim_number", unique=true) private String claimNumber;
    private LocalDate claimDate;
    private Long amountClaimed;
    private String status; // e.g. OPEN, APPROVED, REJECTED
    private String filePath;

    @ManyToOne
    @JoinColumn(name="policy_id")
    @JsonIgnore
    private Policy policy;
}
