package com.example.app2;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;

@Service
@Validated
public class MessageService {
    public List<String> register(@Valid Message message) {
        return null;
    }
}