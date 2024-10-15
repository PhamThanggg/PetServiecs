package com.project.petService.controllers;

import com.nimbusds.jose.JOSEException;
import com.project.petService.dtos.requests.users.AuthenticationRequest;
import com.project.petService.dtos.requests.users.TokenRequest;
import com.project.petService.dtos.response.ApiResponse;
import com.project.petService.dtos.response.users.AuthenticationResponse;
import com.project.petService.dtos.response.users.IntrospectResponse;
import com.project.petService.services.user.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/token")
    public ApiResponse<AuthenticationResponse> token(@RequestBody @Valid AuthenticationRequest request){
        return ApiResponse.<AuthenticationResponse>builder()
                .result(authenticationService.authentication(request))
                .build();
    }

    @PostMapping("/introspect")
    public ApiResponse<IntrospectResponse> tokenIntrospect(@RequestBody @Valid TokenRequest request)
            throws ParseException, JOSEException {
        return ApiResponse.<IntrospectResponse>builder()
                .result(authenticationService.introspect(request))
                .build();
    }

    @PostMapping("/logout")
    ApiResponse<Void> logout(@RequestBody TokenRequest request) throws ParseException, JOSEException {
        authenticationService.logout(request);
        return ApiResponse.<Void>builder().build();
    }

    @PostMapping("/refresh")
    ApiResponse<AuthenticationResponse> refreshToken(@RequestBody TokenRequest request)
            throws ParseException, JOSEException {
        var result = authenticationService.refreshToken(request);
        return ApiResponse.<AuthenticationResponse>builder().result(result).build();
    }
}
