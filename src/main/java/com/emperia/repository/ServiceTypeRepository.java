package com.emperia.repository;

import com.emperia.entity.ServiceType;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

@Transactional
public interface ServiceTypeRepository extends JpaRepository<ServiceType, Long> {
    ServiceType findByType(String type);
}
