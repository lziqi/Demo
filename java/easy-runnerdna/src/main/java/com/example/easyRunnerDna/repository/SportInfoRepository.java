package com.example.easyRunnerDna.repository;

import com.example.easyRunnerDna.entity.SportInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SportInfoRepository extends JpaRepository<SportInfo,String> {
}
