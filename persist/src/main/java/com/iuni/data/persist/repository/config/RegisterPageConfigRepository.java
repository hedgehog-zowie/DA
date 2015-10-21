package com.iuni.data.persist.repository.config;

import com.iuni.data.persist.domain.config.RegisterPage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

public interface RegisterPageConfigRepository extends JpaRepository<RegisterPage, Long>,QueryDslPredicateExecutor<RegisterPage> {

}
