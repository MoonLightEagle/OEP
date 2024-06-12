package com.oep.repository;

import com.oep.domain.Exam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface ExamRepository extends JpaRepository<Exam, Long>, QuerydslPredicateExecutor<Exam> {
}
