package io.xiaotan.ppmtool.services;

import io.xiaotan.ppmtool.domain.User;
import io.xiaotan.ppmtool.exceptions.UserAlreadyExistException;
import io.xiaotan.ppmtool.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    public User saveUser(User newUser){

        try {
            newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));

            //User has to be unique
            newUser.setUsername(newUser.getUsername());
            //password and confirm password match
            //do not persist or show the confirmPassword

            return userRepository.save(newUser);
        }catch (Exception e){
            throw new UserAlreadyExistException("Username '" + newUser.getUsername() + " ' already exists");
        }

    }
}
