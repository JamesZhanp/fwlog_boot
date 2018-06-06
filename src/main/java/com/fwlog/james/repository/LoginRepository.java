package com.fwlog.james.repository;

import com.fwlog.james.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * created by jamesZhan on 2018/01/28
 */
@Repository
public interface LoginRepository extends CrudRepository<User , Integer>{
    User findByName(String name);
    List<User> findByNameAndPassword(String name,String password);
}
