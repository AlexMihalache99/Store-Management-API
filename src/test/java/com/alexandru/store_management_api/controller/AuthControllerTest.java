package com.alexandru.store_management_api.controller;

import com.alexandru.store_management_api.security.JwtUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    AuthenticationManager authenticationManager;

    @Mock
    JwtUtil jwtUtil;

    @Mock
    UserDetailsService userDetailsService;

    @InjectMocks
    AuthController controller;

    @Test
    void login_returnsToken() {
        when(authenticationManager.authenticate(any())).thenReturn(new UsernamePasswordAuthenticationToken("admin", null));
        when(userDetailsService.loadUserByUsername("admin")).thenReturn(org.springframework.security.core.userdetails.User.withUsername("admin").password("x").roles("ADMIN").build());
        when(jwtUtil.generateToken(any(UserDetails.class))).thenReturn("tok-123");

        ResponseEntity<Map<String, String>> res = controller.login(Map.of("username", "admin", "password", "adminpass"));

        assertThat(res.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(res.getBody()).containsKey("token");
        assertThat(res.getBody().get("token")).isEqualTo("tok-123");
    }
}
