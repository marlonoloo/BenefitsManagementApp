package com.emtech.BackendApp.Authentication;

import com.emtech.BackendApp.Dto.RegisterRequest;
import com.emtech.BackendApp.Employee.Employee;
import com.emtech.BackendApp.Employee.EmployeeRepo;
import com.emtech.BackendApp.Exception.BenefitsException;
import com.emtech.BackendApp.Mail.MailService;
import com.emtech.BackendApp.Mail.NotificationEmail;
import com.emtech.BackendApp.Verification.VerificationToken;
import com.emtech.BackendApp.Verification.VerificationTokenRepo;
import lombok.AllArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AuthService {
    private final PasswordEncoder passwordEncoder;
    private final EmployeeRepo employeeRepo;
    private final VerificationTokenRepo verificationTokenRepo;
    private final MailService mailService;

    @Transactional
    public void signup(RegisterRequest registerRequest){
        Employee employee = new Employee();
        employee.setFirstName(registerRequest.getFirstName());
        employee.setLastName(registerRequest.getLastName());
        employee.setEmail(registerRequest.getEmail());
        employee.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        employee.setEnabled(false);

        employeeRepo.save(employee);

        String token = generateVerificationToken(employee);
        mailService.sendMail(new NotificationEmail("Please Activate your Account", employee.getEmail(),
                "Click the link below o activate your account:" +
                        "http://localhost:8090/api/auth/accountVerification/" + token));
    }
    private String generateVerificationToken(Employee employee){
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setEmployee(employee);
        verificationToken.setCreatedAt(LocalDateTime.now());
        verificationToken.setExpiresAt(verificationToken.getCreatedAt().plusMinutes(30));

        verificationTokenRepo.save(verificationToken);
        return token;
    }

    public String verifyAccount(String token) {
        Optional<VerificationToken> verificationToken = verificationTokenRepo.findByToken(token);
        verificationToken.orElseThrow(()-> new BenefitsException("Invalid Token"));
        return fetchUserAndEnable(verificationToken.get());
    }

    @Transactional
    public String fetchUserAndEnable(VerificationToken verificationToken) {
        String email = verificationToken.getEmployee().getEmail();
        Employee employee = employeeRepo.findByEmail(email).orElseThrow(()->
                new BenefitsException("User not found with email - " + email ));

        verificationToken.setConfirmedAt(LocalDateTime.now());
        verificationTokenRepo.save(verificationToken);
        if(verificationToken.getConfirmedAt().isBefore(verificationToken.getExpiresAt())){
            employee.setEnabled(true);
            employeeRepo.save(employee);
            return "Account activated successfully";
        }
        else {
            return "Activation link expired";
        }
    }

}
