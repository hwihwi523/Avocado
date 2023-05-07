package com.avocado.payment.repository;

import com.avocado.payment.entity.Merchandise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MerchandiseRepository extends JpaRepository<Merchandise, Long> {
    List<Merchandise> findByIdIn(List<Long> merchandiseIds);
}
