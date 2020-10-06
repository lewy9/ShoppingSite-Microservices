package com.example.demodeal.repository;

import com.example.demodeal.domain.Goods;
import com.example.demodeal.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {

    public User findByName(String name);
}
