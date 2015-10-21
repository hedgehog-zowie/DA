package com.iuni.data.persist.repository.config;

import com.iuni.data.persist.domain.config.Holiday;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public interface HolidayRepository extends JpaRepository<Holiday, Long> {

    List<Holiday> findByStatusAndCancelFlag(int status, int cancelFlag);

    // add old codes
    @Query("select t from Holiday t where t.cancelFlag=:cancelFlag and t.yr=:year")
    public List<Holiday> findHolidays(@Param("cancelFlag")Integer cancelFlag,@Param("year")Integer year);

    @Query("select t from Holiday t where t.cancelFlag=:cancelFlag and t.yr=:year")
    public Page<Holiday> findHolidays2(@Param("cancelFlag")Integer cancelFlag,@Param("year")Integer year,Pageable pageable);

    @Query("select t from Holiday t left join t.holidayType " +
            "where t.startDate>=:date and t.endDate<=:date and t.holidayType.id in(1,3)" +
            "and t.cancelFlag=0")
    public Holiday isHoliday(@Param("date")Date date);

}
