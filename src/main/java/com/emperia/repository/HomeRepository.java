package com.emperia.repository;

import com.emperia.entity.HomeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface HomeRepository extends JpaRepository<HomeEntity, Long>
{
    @Query(value = "SELECT * FROM home WHERE user_id = ?1", nativeQuery = true)
    List<HomeEntity> findByUserId(Long userId);

    @Query(value = "SELECT * FROM home WHERE name like %:name%", nativeQuery = true)
    List<HomeEntity> search(@Param("name") String name);

    //List<HomeEntity> findByPriceBetween(float bottom, float top);

    //List<HomeEntity> findByUserId(Long userId);
}
