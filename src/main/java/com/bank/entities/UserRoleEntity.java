package com.bank.entities;

import com.bank.models.UserRole;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;


@Entity
@Table(name = "roles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    @Enumerated(EnumType.STRING)
    @NotBlank
    private UserRole name;

}
