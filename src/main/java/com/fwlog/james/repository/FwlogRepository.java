package com.fwlog.james.repository;

import com.fwlog.james.entity.Fwlog;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

//    @Modifying
//    @Query("from Fwlog fw where fw.time >=st and fw.time <= :et and fw.destip = :ip")
//    List<Fwlog> findFwlogsByDestipAndTime(@Param("st")String startTime,@Param("et")String endTime,@Param("ip") Long id);
}
