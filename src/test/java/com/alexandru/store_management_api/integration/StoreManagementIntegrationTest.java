package com.alexandru.store_management_api.integration;

import com.alexandru.store_management_api.dto.CreateProductRequest;
import com.alexandru.store_management_api.dto.ProductResponse;
import com.alexandru.store_management_api.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.math.BigDecimal;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StoreManagementIntegrationTest {

    @Autowired
    private TestRestTemplate rest;

    @Test
    void product_crud_flow_integration() {
        CreateProductRequest req = new CreateProductRequest("IntegrationProduct", "desc", BigDecimal.valueOf(12.34), 7);

        // obtain JWT token
        ResponseEntity<Map> auth = rest.postForEntity("/auth/login", Map.of("username", "admin", "password", "adminpass"), Map.class);
        String token = (String) auth.getBody().get("token");

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<CreateProductRequest> request = new HttpEntity<>(req, headers);

        ResponseEntity<ProductResponse> post = rest.exchange("/products", HttpMethod.POST, request, ProductResponse.class);
        assertThat(post.getStatusCode()).isEqualTo(HttpStatus.OK);
        ProductResponse created = post.getBody();
        assertThat(created).isNotNull();
        assertThat(created.name()).isEqualTo("IntegrationProduct");
        ResponseEntity<ProductResponse> get = rest.exchange("/products/" + created.id(), HttpMethod.GET, new HttpEntity<>(headers), ProductResponse.class);
        assertThat(get.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(get.getBody().id()).isEqualTo(created.id());
    }

    @Test
    void user_create_and_get_integration() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> user = Map.of(
                "username", "int_user",
                "password", "pass",
                "role", "USER"
        );

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(user, headers);

        ResponseEntity<Map> auth = rest.postForEntity("/auth/login", Map.of("username", "admin", "password", "adminpass"), Map.class);
        String token = (String) auth.getBody().get("token");
        HttpHeaders authHeaders = new HttpHeaders();
        authHeaders.setBearerAuth(token);
        authHeaders.setContentType(MediaType.APPLICATION_JSON);

        ResponseEntity<User> post = rest.exchange("/users", HttpMethod.POST, new HttpEntity<>(user, authHeaders), User.class);
        assertThat(post.getStatusCode()).isEqualTo(HttpStatus.OK);
        User created = post.getBody();
        assertThat(created).isNotNull();
        assertThat(created.getUsername()).isEqualTo("int_user");
        ResponseEntity<User> get = rest.exchange("/users/" + created.getId(), HttpMethod.GET, new HttpEntity<>(authHeaders), User.class);
        assertThat(get.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(get.getBody().getUsername()).isEqualTo("int_user");
    }
}
