package com.oep.repository;

import com.oep.domain.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface QuestionRepository extends JpaRepository<Question, Long>, QuerydslPredicateExecutor<Question> {
}
