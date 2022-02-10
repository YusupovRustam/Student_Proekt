package com.company.repository;

import com.company.entity.FileStorage;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface FileStorageRepository extends JpaRepository<FileStorage,Long>{

    FileStorage findByUserId(Long userId);

}
