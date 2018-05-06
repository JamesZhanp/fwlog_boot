package com.fwlog.james.repository;

import com.fwlog.james.entity.Advice;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.Table;
import java.util.List;

@Repository
@Table(name = "advice")
@Qualifier("adviceRepository")
public interface AdviceRepository extends JpaRepository<Advice,Integer> {
    List<Advice> findAdviceByType(String type);
}
