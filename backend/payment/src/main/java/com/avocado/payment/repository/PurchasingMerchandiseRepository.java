package com.avocado.payment.repository;

import com.avocado.payment.entity.PurchasingMerchandise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PurchasingMerchandiseRepository extends JpaRepository<PurchasingMerchandise, Long> {
    List<PurchasingMerchandise> findByPurchasing_Id(UUID purchasingId);

    @Query("select pm.merchandiseId from PurchasingMerchandise pm where pm.purchasing.id = :purchasingId")
    List<Long> findMerchandiseIdsByPurchasing_Id(@Param("purchasingId") UUID purchasingId);
}
