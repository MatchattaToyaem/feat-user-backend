package com.example.feat_user.repositories;

import com.example.feat_user.models.Users;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UsersRepository extends MongoRepository<Users, String> {
    List<Users> findUsersByEmail(String email);
    void deleteByEmail(String email);
}
