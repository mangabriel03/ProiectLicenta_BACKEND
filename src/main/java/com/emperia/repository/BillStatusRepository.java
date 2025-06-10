package com.emperia.repository;

import com.emperia.entity.BillStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

@Transactional
public interface BillStatusRepository extends JpaRepository<BillStatusEntity, Long>{

}
