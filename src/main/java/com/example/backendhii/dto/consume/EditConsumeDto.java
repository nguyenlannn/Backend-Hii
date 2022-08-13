package com.example.backendhii.dto.consume;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EditConsumeDto implements Serializable {

    private String firstName;

    private String middleName;

    private String lastName;
}
