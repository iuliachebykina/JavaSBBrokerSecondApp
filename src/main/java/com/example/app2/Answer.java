package com.example.app2;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Answer {
    private int id;
    private State state;
    private String time;
    private String message;
}
enum State{
    VALID,
    INVALID
}