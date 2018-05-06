package com.fwlog.james.repository;

import com.fwlog.james.entity.Suspectedevents;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.Table;
import java.util.Date;
import java.util.List;

@Repository
@Table(name="suspectedevents")
@Qualifier("suspectedeventsRepository")
public interface SuspectedeventsRepository extends JpaRepository<Suspectedevents,Long> {
    Suspectedevents save(Suspectedevents suspectedevents);

    List<Suspectedevents> findAll();

    @Query("select se from Suspectedevents se where se.stime > :st and se.etime < :et")
    List<Suspectedevents> findByStimeAfterAndEtimeBefore(@Param("st")Date st,@Param("et") Date et);
}
