package com.icms.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="policy")
public class Policy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="policy_number", unique=true) private String policyNumber;
    private String type;
    private Long coverageAmount;
    private LocalDate startDate;
    private LocalDate endDate;

    @ManyToOne
    @JoinColumn(name="policy_holder_id")
    private PolicyHolder policyHolder;

    @OneToMany(mappedBy="policy", cascade = CascadeType.ALL)
    private List<Claim> claims = new ArrayList<>();
}
