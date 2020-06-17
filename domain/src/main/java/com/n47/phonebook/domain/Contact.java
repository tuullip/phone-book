package com.n47.phonebook.domain;

import com.n47.phonebook.domain.validation.PhoneNumberConstraint;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Contact {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank(message = "field_name")
    private String name;

    @PhoneNumberConstraint
    private String phoneNumber;
}
