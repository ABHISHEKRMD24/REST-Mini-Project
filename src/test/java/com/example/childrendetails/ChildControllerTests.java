package com.example.childrendetails;

import com.example.childrendetails.controller.ChildController;
import com.example.childrendetails.dto.ChildCreateRequest;
import com.example.childrendetails.dto.ChildCreateResponse;
import com.example.childrendetails.dto.ChildPageResponse;
import com.example.childrendetails.service.ChildService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class ChildControllerTests {

    private ChildService childService;
    private ChildController childController;

    @BeforeEach
    void setUp() {
        childService = new ChildService();
        childController = new ChildController(childService);
    }

    @Test
    void testGetChildrenDefaultPagination() {
        ResponseEntity<ChildPageResponse> response = childController.getChildren(1, "name", 10);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();

        ChildPageResponse page = response.getBody();
        assertThat(page.getCurrentPage()).isEqualTo(1);
        assertThat(page.getTotalPages()).isEqualTo(4);
        assertThat(page.getTotalRecords()).isEqualTo(40);
        assertThat(page.getChildren()).hasSize(10);
        // Default alphabetical sort: Alice Johnson first
        assertThat(page.getChildren().get(0).getName()).isEqualTo("Alice Johnson");
    }

    @Test
    void testGetChildrenSortNameDesc() {
        ResponseEntity<ChildPageResponse> response = childController.getChildren(1, "name-desc", 10);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();

        ChildPageResponse page = response.getBody();
        // Zachary Campbell first
        assertThat(page.getChildren().get(0).getName()).isEqualTo("Zachary Campbell");
    }

    @Test
    void testGetChildrenSortAgeAsc() {
        ResponseEntity<ChildPageResponse> response = childController.getChildren(1, "age-asc", 10);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();

        ChildPageResponse page = response.getBody();
        // Minimum age should be 4
        assertThat(page.getChildren().get(0).getAge()).isEqualTo(4);
    }

    @Test
    void testGetChildrenSortAgeDesc() {
        ResponseEntity<ChildPageResponse> response = childController.getChildren(1, "age-desc", 10);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();

        ChildPageResponse page = response.getBody();
        // Maximum age should be 12
        assertThat(page.getChildren().get(0).getAge()).isEqualTo(12);
    }

    @Test
    void testGetChildrenPaginationNavigation() {
        ResponseEntity<ChildPageResponse> page2 = childController.getChildren(2, "name", 10);
        assertThat(page2.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(page2.getBody()).isNotNull();
        assertThat(page2.getBody().getCurrentPage()).isEqualTo(2);
        assertThat(page2.getBody().getChildren()).hasSize(10);

        ResponseEntity<ChildPageResponse> page4 = childController.getChildren(4, "name", 10);
        assertThat(page4.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(page4.getBody()).isNotNull();
        assertThat(page4.getBody().getCurrentPage()).isEqualTo(4);
        assertThat(page4.getBody().getChildren()).hasSize(10);
    }

    @Test
    void testAddChildSuccess() {
        ChildCreateRequest request = new ChildCreateRequest("Oliver Queen", 8, "Male");
        ResponseEntity<?> response = childController.addChild(request);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isInstanceOf(ChildCreateResponse.class);

        ChildCreateResponse body = (ChildCreateResponse) response.getBody();
        assertThat(body.getMessage()).isEqualTo("Child added successfully!");
        assertThat(body.getChild().getId()).isEqualTo(41L);
        assertThat(body.getChild().getName()).isEqualTo("Oliver Queen");

        // Verify total records incremented to 41
        ResponseEntity<ChildPageResponse> pagedResponse = childController.getChildren(1, "name", 10);
        assertThat(pagedResponse.getBody().getTotalRecords()).isEqualTo(41);
        assertThat(pagedResponse.getBody().getTotalPages()).isEqualTo(5);
    }

    @Test
    void testAddChildValidationMissingFields() {
        ChildCreateRequest request = new ChildCreateRequest("", 8, "Male");
        ResponseEntity<?> response = childController.addChild(request);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        Map<?, ?> body = (Map<?, ?>) response.getBody();
        assertThat(body.get("error")).isEqualTo("Name, age, and gender are required.");
    }

    @Test
    void testAddChildValidationNegativeAge() {
        ChildCreateRequest request = new ChildCreateRequest("Negative Age Child", -1, "Male");
        ResponseEntity<?> response = childController.addChild(request);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        Map<?, ?> body = (Map<?, ?>) response.getBody();
        assertThat(body.get("error")).isEqualTo("Please enter a valid age.");
    }
}
