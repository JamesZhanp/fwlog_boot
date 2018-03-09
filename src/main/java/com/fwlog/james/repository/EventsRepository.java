package com.fwlog.james.repository;

import com.fwlog.james.entity.Events;
import com.fwlog.james.mode.EventEntity;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.Table;
import java.util.List;

@Repository
@Qualifier("eventsRepository")
@Table(name = "events")
public interface EventsRepository extends JpaRepository<Events,Long> {
    Events save(Events events);

    List<Events> findAll();
}
