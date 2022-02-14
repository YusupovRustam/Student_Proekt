package com.company.repository;

import com.company.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {


    @Query(value = "select count(*) from student3 s where s.id=:id", nativeQuery = true)
    int countById(Long id);

    @Query(value = "select count(*) from student3 s where s.phone=:phone", nativeQuery = true)
    int countByPhone(String phone);

}
