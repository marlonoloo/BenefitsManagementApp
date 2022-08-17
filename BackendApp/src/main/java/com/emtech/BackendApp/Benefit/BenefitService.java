package com.emtech.BackendApp.Benefit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class BenefitService {
    private final BenefitRepo benefitRepo;

    public BenefitService(BenefitRepo benefitRepo) {
        this.benefitRepo = benefitRepo;
    }

    public Benefit addBenefit(Benefit benefit){
        return benefitRepo.save(benefit);
    }

    public Benefit updateBenefit(Benefit benefit){
        return benefitRepo.save(benefit);
    }

    public List<Benefit> findAllBenefits(){
        return benefitRepo.findAll();
    }

    public void deleteBenefit(Long benefitId){
        benefitRepo.deleteById(benefitId);
    }

}
