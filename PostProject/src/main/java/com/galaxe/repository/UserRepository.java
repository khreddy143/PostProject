package com.galaxe.repository;
import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import com.galaxe.entity.UserEntity;
@Transactional
public interface UserRepository extends CrudRepository<UserEntity, Long>{
}