package com.emperia.repository;

import javax.transaction.Transactional;


import com.emperia.entity.ProviderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

//@Repository
@Transactional
public interface ProviderRepository extends JpaRepository<ProviderEntity, Long>{
    ProviderEntity findByName(String name);
}
