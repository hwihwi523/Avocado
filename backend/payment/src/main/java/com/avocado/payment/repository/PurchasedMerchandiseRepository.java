package com.avocado.payment.repository;

import com.avocado.payment.entity.PurchasedMerchandise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchasedMerchandiseRepository extends JpaRepository<PurchasedMerchandise, Long> {
}
