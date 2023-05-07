package com.avocado.payment.repository;

import com.avocado.payment.entity.Purchasing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PurchasingRepository extends JpaRepository<Purchasing, UUID> {
}
