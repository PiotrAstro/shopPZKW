package com.example.shoppzkw.authentication;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<ShopUser,Integer> {
    public Optional<ShopUser> findByUserName(String name);
}
