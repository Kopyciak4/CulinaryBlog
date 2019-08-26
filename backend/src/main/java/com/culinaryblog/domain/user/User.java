package com.culinaryblog.domain.user;


import com.culinaryblog.validation.EmptyPasswordValidation;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Entity
public class User implements Serializable {


    @NotNull
    @Size(min=1, max=16)
    @Column(nullable = false, unique = true)
    private String login;
    @NotNull
    @NotEmpty(groups = {EmptyPasswordValidation.class})
    @Size(max=60)
    @Column(nullable = false)
    private String password;
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int userID;

    public User(){

    }

    public String getLogin(){
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public int getUserID(){
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }


}
