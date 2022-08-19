package com.emtech.BackendApp.Authentication;

import com.emtech.BackendApp.Dto.RegisterRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth/")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;
    @PostMapping("signup")
    public ResponseEntity<String> signupController(@RequestBody RegisterRequest registerRequest){
        authService.signup(registerRequest);
        return new ResponseEntity<>("User registration successful", HttpStatus.OK);
    }

    @GetMapping("accountVerification/{token}")
    public ResponseEntity<String> verifyAccountController(@PathVariable String token){
        authService.verifyAccount(token);
        return new ResponseEntity<>(authService.verifyAccount(token), HttpStatus.OK);
    }




}
