package com.iuni.data.persist.repository.config;

import com.iuni.data.persist.domain.config.Advertisement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

public interface AdvertisementRepository extends JpaRepository<Advertisement, Long>, JpaSpecificationExecutor<Advertisement>, QueryDslPredicateExecutor<Advertisement> {
}
