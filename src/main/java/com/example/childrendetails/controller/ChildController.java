package com.example.childrendetails.controller;

import com.example.childrendetails.dto.ChildCreateRequest;
import com.example.childrendetails.dto.ChildCreateResponse;
import com.example.childrendetails.dto.ChildPageResponse;
import com.example.childrendetails.model.Child;
import com.example.childrendetails.service.ChildService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/children")
@CrossOrigin(origins = "*")
public class ChildController {

    private final ChildService childService;

    public ChildController(ChildService childService) {
        this.childService = childService;
    }

    @GetMapping
    public ResponseEntity<ChildPageResponse> getChildren(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "10") int limit) {
        ChildPageResponse response = childService.getChildren(page, sortBy, limit);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<?> addChild(@RequestBody ChildCreateRequest request) {
        if (request.getName() == null || request.getName().trim().isEmpty() ||
                request.getAge() == null ||
                request.getGender() == null || request.getGender().trim().isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Name, age, and gender are required."));
        }

        if (request.getAge() < 0) {
            return ResponseEntity.badRequest().body(Map.of("error", "Please enter a valid age."));
        }

        Child newChild = childService.addChild(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ChildCreateResponse("Child added successfully!", newChild));
    }
}
