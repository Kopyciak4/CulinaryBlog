package com.culinaryblog.services.user;


import com.culinaryblog.domain.user.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    void register(User user);

    User getUser(String login);



}
