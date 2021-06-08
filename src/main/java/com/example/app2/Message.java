package com.example.app2;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@Entity(name = "user")
public class Message {
    @Id
    private long id;
    @NotBlank
    @Size(min = 3, max = 50, message = "Слишком большое или слишком маленькое имя. Размер имени должен быть от 3 до 50 знаков")
    private String name;
    @Pattern(regexp = "^7[0-9]{10}", message = "Неправильно введен номер телефона")
    private String phoneNumber;

    public Message(){

    }
}
