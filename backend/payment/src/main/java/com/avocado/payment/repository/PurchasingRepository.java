package com.avocado.payment.repository;

import com.avocado.payment.entity.redis.Purchasing;
import org.springframework.data.repository.CrudRepository;

public interface PurchasingRepository extends CrudRepository<Purchasing, String> {
}
