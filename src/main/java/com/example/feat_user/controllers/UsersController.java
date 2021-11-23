package com.example.feat_user.controllers;

import com.example.feat_user.models.Users;
import com.example.feat_user.repositories.UsersRepository;
import com.example.feat_user.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/users")
public class UsersController {
    @Autowired
    private UsersService usersService;

    @GetMapping()
    public ResponseEntity<Users> getUsers(@RequestHeader("user-email") String email) throws Exception {
        Users users = usersService.getUser(email);
        return ResponseEntity.ok(users);
    }

    @PostMapping()
    public ResponseEntity<?> createUsers(@Validated @RequestBody Users user){
        try{
            usersService.createUser(user);
        }
        catch (IOException ioException){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateUsers(@Validated @RequestBody Users user){
        try{
            usersService.updateUser(user);
        }
        catch (IOException ioException){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
