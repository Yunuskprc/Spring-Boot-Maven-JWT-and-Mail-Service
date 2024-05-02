package com.jwt.auth.Repository;

import com.jwt.auth.Entity.Modal.MailVerify;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MailVerifyRepo extends JpaRepository<MailVerify,Integer> {
    Optional<MailVerify> findByUserId(Integer id);

    void deleteByUserId(Integer id);
}
