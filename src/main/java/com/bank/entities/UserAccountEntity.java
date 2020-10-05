package com.bank.entities;

import com.bank.models.AccountType;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
@Table(name = "accounts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @NotNull
    private AccountType type;

    @Column(unique = true)
    private UUID cbu;

    private Double amount;

    public UserAccountEntity(@NotEmpty AccountType type, UUID cbu) {
        this.type = type;
        this.cbu = cbu;
    }
}
