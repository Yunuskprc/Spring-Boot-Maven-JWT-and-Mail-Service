package com.jwt.auth.Repository;

import com.jwt.auth.Entity.Modal.User;
import com.jwt.auth.Entity.Modal.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserInfoRepo extends JpaRepository<UserInfo,Integer> {

    Optional<UserInfo> findByUser(User user);


}
