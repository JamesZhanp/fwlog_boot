package com.fwlog.james.repository;

import com.fwlog.james.entity.Destport;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.Table;

@Repository
@Table(name = "destport")
public interface DestportRepository extends JpaRepository<Destport,Long>{

}
