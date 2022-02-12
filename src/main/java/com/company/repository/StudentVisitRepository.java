package com.company.repository;

import com.company.entity.StudentVisit;
import com.company.enums.VisitStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentVisitRepository extends JpaRepository<StudentVisit,Long> {


    List<StudentVisit> findAllByVisitStatus(VisitStatus visitStatus);
}
