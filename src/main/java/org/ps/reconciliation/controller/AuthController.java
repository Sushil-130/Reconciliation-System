package org.ps.reconciliation.controller;

import org.ps.reconciliation.dto.AuthRequest;
import org.ps.reconciliation.model.User;
import org.ps.reconciliation.service.AuthService;
import org.ps.reconciliation.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@ResponseBody
public class AuthController {
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AuthService authService;


    @PostMapping("/signup")
    public ResponseEntity<String> register(@RequestBody AuthRequest authRequest){
        authService.signUp(authRequest);
        return new ResponseEntity<>("User Registration Successful",
                HttpStatus.OK);
    }


    @PostMapping("/authenticate")
    public ResponseEntity<?> generateToken(@RequestBody AuthRequest authRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authRequest.getUsername(),
                            authRequest.getPassword())
            );
        } catch (Exception ex) {
            throw new Exception("inavalid username/password");
        }
        return new ResponseEntity<>(jwtUtil.generateToken(authRequest.getUsername())
                ,HttpStatus.OK);
    }

//    @PostMapping("/refresh/token")
//    public AuthenticationResponse refreshTokens(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
//        return authService.refreshToken(refreshTokenRequest);
//    }


}
