package com.micro.nov23;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.micro.nov23.model.Student;

@Repository
public interface StudentRepository extends JpaRepository <Student,String>{

}
