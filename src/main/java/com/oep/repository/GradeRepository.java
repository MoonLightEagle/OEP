package com.oep.repository;

import com.oep.domain.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;
import java.util.Optional;

public interface GradeRepository extends JpaRepository<Grade, Long>, QuerydslPredicateExecutor<Grade> {


}
