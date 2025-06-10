package com.emperia.repository;

import com.emperia.entity.LocationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

@Transactional
public interface LocationRepository extends JpaRepository<LocationEntity, Long>
{
}
