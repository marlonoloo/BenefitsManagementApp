package com.emtech.BackendApp.Benefit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(path = "api/auth/benefits/")
@Slf4j
public class BenefitController {
    @Autowired
    private final BenefitService benefitService;

    public BenefitController(BenefitService benefitService) {
        this.benefitService = benefitService;
    }

    @PostMapping("add")
    public ResponseEntity<Benefit> addBenefitController(@RequestBody Benefit benefit){
        Benefit newBenefit = benefitService.saveBenefit(benefit);
        return new ResponseEntity<>(newBenefit, HttpStatus.CREATED);
    }

    @PutMapping("edit")
    public ResponseEntity<Benefit> updateBenefitController(@RequestBody Benefit benefit){
        Benefit newBenefit = benefitService.saveBenefit(benefit);
        return new ResponseEntity<>(newBenefit, HttpStatus.OK);
    }

    @GetMapping("find/all")
    public ResponseEntity<List<Benefit>> findAllBenefitsController(){
        List<Benefit> benefitList = benefitService.findAllBenefits();
        return new ResponseEntity<>(benefitList, HttpStatus.OK);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> deleteBenefitController(@PathVariable("id") Long id){
        benefitService.deleteBenefit(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
