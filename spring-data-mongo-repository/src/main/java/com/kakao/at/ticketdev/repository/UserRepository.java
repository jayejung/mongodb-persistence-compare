package com.kakao.at.ticketdev.repository;

import com.kakao.at.ticketdev.user.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface UserRepository extends MongoRepository<UserEntity, String>, QuerydslPredicateExecutor<UserEntity> {
    @Query(value = "{}", fields = "{userType: 1}")
    List<UserEntity> findAll();

    @Query(value = "{'userType': ?0}", fields = "{userType: 1}")
    List<UserEntity> findByUserType(String userType);
}
