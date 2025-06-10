package com.emperia.repository;



import com.emperia.entity.BillEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Transactional
public interface BillRepository extends JpaRepository<BillEntity, Long> {
    List<BillEntity> findByHomeId(Long homeId);

    @Query(value = "SELECT * FROM bill WHERE issue_date BETWEEN :startDate AND :endDate", nativeQuery = true)
    List<BillEntity> findBillsGeneratedBetween(@Param("startDate") Date startDate, @Param("endDate") Date endDate);


    BillEntity findFirstById(Long id);

    @Modifying
    @Query(value = "DELETE FROM favorite_home WHERE home_id = :homeId", nativeQuery = true)
    void deleteById(@Param("homeId") Long idNumeric);

    void delete(BillEntity billEntity);

}
