package com.bank.entities;

import com.bank.models.UserGender;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Entity
@Table(name = "info")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @NotBlank
    private String name;

    @Column(name = "last_name")
//    @NotBlank
    private String lastName;

    @Enumerated(EnumType.STRING)
    private UserGender gender;

    @Column(name = "birth_date")
//    @NotBlank
    private LocalDate birthDate;

    @Column(name = "cel_phone", unique = true)
//    @Digits(integer = 10, fraction = 0)
    private Integer celPhone;

//    @NotBlank
    private String province;

//    @NotBlank
    private String municipality;


}
