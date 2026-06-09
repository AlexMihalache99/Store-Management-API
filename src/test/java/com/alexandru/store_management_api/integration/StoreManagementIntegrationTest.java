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

        ResponseEntity<ProductResponse> post = rest.postForEntity("/products", req, ProductResponse.class);
        assertThat(post.getStatusCode()).isEqualTo(HttpStatus.OK);
        ProductResponse created = post.getBody();
        assertThat(created).isNotNull();
        assertThat(created.name()).isEqualTo("IntegrationProduct");

        ResponseEntity<ProductResponse> get = rest.getForEntity("/products/" + created.id(), ProductResponse.class);
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

        ResponseEntity<User> post = rest.postForEntity("/users", entity, User.class);
        assertThat(post.getStatusCode()).isEqualTo(HttpStatus.OK);
        User created = post.getBody();
        assertThat(created).isNotNull();
        assertThat(created.getUsername()).isEqualTo("int_user");

        ResponseEntity<User> get = rest.getForEntity("/users/" + created.getId(), User.class);
        assertThat(get.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(get.getBody().getUsername()).isEqualTo("int_user");
    }
}
