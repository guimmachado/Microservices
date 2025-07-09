package com.ms.user.services;

import com.ms.user.models.UserModel;
import com.ms.user.producers.UserProducer;
import com.ms.user.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    final UserRepository repo;
    final UserProducer prod;

    public UserService(UserRepository repo, UserProducer prod) {
        this.repo = repo;
        this.prod = prod;
    }

    @Transactional
    public UserModel save(UserModel userModel){
        userModel = repo.save(userModel);
        prod.publishEmailMessage(userModel);
        return userModel;
    }

}
