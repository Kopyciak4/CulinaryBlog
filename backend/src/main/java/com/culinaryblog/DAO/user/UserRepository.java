package com.culinaryblog.DAO.user;

import com.culinaryblog.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
        User findByLogin(String login);
}
