package com.fwlog.james.repository;

import com.fwlog.james.entity.Fwlog;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.Table;
import java.util.List;

@Repository
@Table(name = "fwlog")
@Qualifier("fwlogRepository")
public interface FwlogRepository extends JpaRepository<Fwlog,Long> {
    @Override
    List<Fwlog> findAll();

    Fwlog save(Fwlog fwlog);
}
