package com.example.user.services.impl;

import com.example.user.io.entity.UserEntity;
import com.example.user.io.repository.UserRepository;
import com.example.user.services.DataSetupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class DataSetupServiceImpl implements DataSetupService {
    private final UserRepository userRepository;

    @Autowired
    public DataSetupServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // will create data
        if(userRepository.count().block() == 0) {
            UserEntity john = new UserEntity("John", 10000);
            UserEntity paul = new UserEntity("Paul", 10000);
            UserEntity ringo = new UserEntity("Ringo", 7500);
            UserEntity george = new UserEntity("George", 9000);
            Flux.just(john, paul, george, ringo)
                    .flatMap(this.userRepository::save)
                    .doOnNext(System.out::println)
                    .subscribe();
        }
    }
}
