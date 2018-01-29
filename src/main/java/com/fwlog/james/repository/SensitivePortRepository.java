package com.fwlog.james.repository;

import com.fwlog.james.entity.Sensitiveport;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.Table;
import java.util.List;

@Repository
@Table(name = "sensitiveport")
@Qualifier("sensitivePortRepository")
public interface SensitivePortRepository extends JpaRepository<Sensitiveport,Integer>{
    List<Sensitiveport> findByPort(@Param("id")int id);
}
