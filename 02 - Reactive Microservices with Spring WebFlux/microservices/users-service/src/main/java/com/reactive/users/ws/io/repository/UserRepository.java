package com.reactive.users.ws.io.repository;

import com.reactive.users.ws.io.entity.UsersEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends ReactiveCrudRepository<UsersEntity, Long> {
}
