package com.fwlog.james.repository;

import com.fwlog.james.entity.Events;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.Table;

@Repository
@Qualifier("eventsRepository")
@Table(name = "events")
public interface EventsRepository extends JpaRepository<Events,Long> {
    Events save(Events events);
}
