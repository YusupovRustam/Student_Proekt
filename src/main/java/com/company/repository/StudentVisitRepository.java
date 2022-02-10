package com.company.repository;

import com.company.entity.StudentVisit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentVisitRepository extends JpaRepository<StudentVisit,Long> {

}
