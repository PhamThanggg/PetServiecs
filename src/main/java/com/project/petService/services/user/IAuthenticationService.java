package com.project.petService.services.user;

import com.nimbusds.jose.JOSEException;
import com.project.petService.dtos.requests.users.AuthenticationRequest;
import com.project.petService.dtos.requests.users.TokenRequest;
import com.project.petService.dtos.response.users.AuthenticationResponse;
import com.project.petService.dtos.response.users.IntrospectResponse;

import java.text.ParseException;

public interface IAuthenticationService {
    AuthenticationResponse authentication(AuthenticationRequest request);

    IntrospectResponse introspect(TokenRequest request) throws ParseException, JOSEException;


}
