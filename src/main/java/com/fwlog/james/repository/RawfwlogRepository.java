package com.fwlog.james.repository;

import com.fwlog.james.entity.Rawfwlog;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.Table;
import java.util.Date;
import java.util.List;

@Repository
@Table(name="rawfwlog")
@Qualifier("rawfwlogRepository")
public interface RawfwlogRepository extends JpaRepository<Rawfwlog,Long> {
    Rawfwlog save(Rawfwlog rawfwlog);

    List<Rawfwlog> findByProducetimeAfterAndSafetydomain(Date date,int domain);
}
