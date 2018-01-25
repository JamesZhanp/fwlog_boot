package com.fwlog.james.repository;

import com.fwlog.james.entity.Destip;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.Table;
import java.util.List;

@Repository
@Table(name = "destip")
@Qualifier("destipRepository")
public interface DestipRepository extends JpaRepository<Destip,Long>{

    Destip save(Destip destip);

    List<Destip> findAll();
}
