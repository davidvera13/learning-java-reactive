package com.example.user.io.repository;

import com.example.user.io.entity.UserEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface UserRepository extends ReactiveMongoRepository<UserEntity, String> {
}
