package com.iuni.data.persist.repository.config;

import com.iuni.data.persist.domain.config.HolidayType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HolidayTypeRepository extends JpaRepository<HolidayType, Long> {

}
