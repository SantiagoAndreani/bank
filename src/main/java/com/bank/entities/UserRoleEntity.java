package com.bank.entities;

import com.bank.models.UserRole;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "roles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @NotNull
    private UserRole name;

    @Column(name = "is_account_non_exoired")
    private boolean isAccountNonExpired = true;
    @Column(name = "is_account_non_locked")
    private boolean isAccountNonLocked = true;
    @Column(name = "is_credentials_non_expired")
    private boolean isCredentialsNonExpired = true;
    @Column(name = "is_enabled")
    private boolean isEnabled = true;

    public UserRoleEntity(@NotNull UserRole name) {
        this.name = name;
    }

}
