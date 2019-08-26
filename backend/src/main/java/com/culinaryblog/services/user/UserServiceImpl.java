package com.culinaryblog.services.user;


import com.culinaryblog.DAO.user.UserRepository;
import com.culinaryblog.domain.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }



    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }



    @Override
    public void register(User user){
        encryptPassword(user);
        userRepository.save(user);
    }

    @Override
    public User getUser(String login) {
        User user = userRepository.findByLogin(login);
        if(user == null)
            return null;
        user.setPassword("");
        return user;
    }

    private void encryptPassword(User user) {
        user.setPassword( passwordEncoder.encode(user.getPassword()));
    }

}