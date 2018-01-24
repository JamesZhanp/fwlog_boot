package com.fwlog.james.repository;

import com.fwlog.james.entity.Srcip;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.Table;
import java.util.List;

@Repository
@Table(name = "srcip")
@Qualifier("srcipRepository")
public interface SrcipRepository extends JpaRepository<Srcip,Long>{
//    获取所有的数据
    @Override
    List<Srcip> findAll();

    Srcip save(Srcip srcip);
}
