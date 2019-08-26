package com.culinaryblog.web.user;



import com.culinaryblog.domain.user.User;
import com.culinaryblog.services.user.UserService;
import com.culinaryblog.validation.EmptyPasswordValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.groups.Default;


@RestController
@RequestMapping("/user")
public class UserController {

    UserService userService;

    @Autowired
    public void setAccountService( UserService userService) {
        this.userService = userService;
    }

//    @GetMapping("/{login}")
//    public User getAccount(
//            @PathVariable String login){
//        return userService.getUser(login);
//    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerAccount(@Validated({EmptyPasswordValidation.class, Default.class}) @RequestBody User user){
        userService.register(user);
    }







}
