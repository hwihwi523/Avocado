package com.avocado.product.repository;

import com.avocado.product.entity.Click;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClickRepository extends JpaRepository<Click, Long> {
}
