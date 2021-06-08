package com.example.app2;

import org.springframework.data.repository.CrudRepository;

public interface UserRepo extends CrudRepository<Message, Integer> {
}
