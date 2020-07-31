package com.zhy.dao;

import com.zhy.domain.People;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PeopleDao extends JpaRepository<People, Long> {
    @Query(name = "findPeople" , value = "SELECT * FROM people WHERE uname= :uname", nativeQuery = true)
    People findPeople(@Param("uname")String uname);
}
