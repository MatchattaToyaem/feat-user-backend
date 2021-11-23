package com.example.feat_user.services;

import com.example.feat_user.models.Users;

import java.io.IOException;
import java.util.List;

public interface UsersService {
    public abstract void createUser(Users users) throws Exception;
    public abstract Users getUser(String email) throws Exception;
    public abstract void updateUser(Users users) throws Exception;
    public abstract void deleteUser();
}
