package com.example.feat_user.services;

import com.example.feat_user.models.Users;
import com.example.feat_user.repositories.UsersRepository;
import com.example.feat_user.utils.FileUtilities;
import com.example.feat_user.utils.ComputeFootData;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
public class UserServiceImpl implements UsersService{
    private final UsersRepository usersRepository;

    public UserServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public void createUser(Users users) throws Exception {
        usersRepository.insert(new ComputeFootData(users).transform());
    }

    @Override
    public Users getUser(String email) throws Exception {
        List<Users> results = usersRepository.findUsersByEmail(email);
        Users user = null;
        FileUtilities fileUtilities = new FileUtilities();
        if(!CollectionUtils.isEmpty(results)){
            user = results.get(0);
            user.getLeftFoot().setSideImage(fileUtilities.encodeFileToBase64Binary(user.getLeftFoot().getSideImage()));
            user.getLeftFoot().setSoleImage(fileUtilities.encodeFileToBase64Binary(user.getLeftFoot().getSoleImage()));
            user.getRightFoot().setSideImage(fileUtilities.encodeFileToBase64Binary(user.getRightFoot().getSideImage()));
            user.getRightFoot().setSoleImage(fileUtilities.encodeFileToBase64Binary(user.getRightFoot().getSoleImage()));
        }
        return user;
    }

    @Override
    public void updateUser(Users users) throws Exception {
        if(!ObjectUtils.isEmpty(users)){
            Users updatedUser = new ComputeFootData(users).transform();
            usersRepository.save(updatedUser);
        }
    }

    @Override
    public void deleteUser() {

    }
}
