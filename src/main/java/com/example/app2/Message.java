package com.example.app2;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    private int id;
    @NotBlank
    @Size(min = 3, max = 50)
    private String name;
    @Pattern(regexp = "^7[0-9]{10}")
    private String phoneNumber;

}
