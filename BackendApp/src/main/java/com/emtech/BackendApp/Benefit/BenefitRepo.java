package com.emtech.BackendApp.Benefit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BenefitRepo extends JpaRepository<Benefit, Long> {

}
