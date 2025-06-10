package com.emperia.repository;

import com.emperia.entity.ProviderServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

//@Repository
@Transactional
public interface ProviderServicesRepository extends JpaRepository<ProviderServiceEntity, Long> {

}
