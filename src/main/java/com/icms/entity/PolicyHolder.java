package com.icms.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name="policy_holder")
public class PolicyHolder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(unique=true) private String email;
    private LocalDate dob;
    private String phone;

    @OneToMany(mappedBy="policyHolder", cascade = CascadeType.ALL)
    @JsonIgnore // prevent recursion
    private List<Policy> policies = new ArrayList<>();
}
