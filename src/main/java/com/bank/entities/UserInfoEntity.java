package com.bank.entities;

import com.bank.models.UserGender;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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

    @NotBlank(message = "Nombre sin espacios")
    private String name;

    @Column(name = "last_name")
    @NotBlank(message = "Apellido sin espacios")
    private String lastName;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Eliga un genero")
    private UserGender gender;

    @Column(name = "birth_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Eliga una fecha")
    private LocalDate birthDate;

    @Column(name = "cel_phone", unique = true)
    @NotBlank(message = "Telefono sin espacios")
    private Integer celPhone;

    @NotBlank(message = "Eliga su provincia")
    private String province;

    @NotBlank(message = "Eliga su municipio")
    private String municipality;

}
