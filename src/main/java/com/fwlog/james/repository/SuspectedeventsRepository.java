package com.fwlog.james.repository;

import com.fwlog.james.entity.Suspectedevents;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.Table;

@Repository
@Table(name="suspectedevents")
@Qualifier("suspectedeventsRepository")
public interface SuspectedeventsRepository extends JpaRepository<Suspectedevents,Long> {
    Suspectedevents save(Suspectedevents suspectedevents);
}
