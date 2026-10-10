package com.example.childrendetails.service;

import com.example.childrendetails.dto.ChildCreateRequest;
import com.example.childrendetails.dto.ChildPageResponse;
import com.example.childrendetails.model.Child;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ChildService {

    private final List<Child> children = new CopyOnWriteArrayList<>();
    private final AtomicLong nextId = new AtomicLong(41);

    public ChildService() {
        initSampleData();
    }

    private void initSampleData() {
        children.add(new Child(1L, "Alice Johnson", 7, "Female"));
        children.add(new Child(2L, "Bob Smith", 5, "Male"));
        children.add(new Child(3L, "Charlie Brown", 9, "Male"));
        children.add(new Child(4L, "Daisy Miller", 6, "Female"));
        children.add(new Child(5L, "Ethan Davis", 8, "Male"));
        children.add(new Child(6L, "Fiona White", 4, "Female"));
        children.add(new Child(7L, "George Clark", 10, "Male"));
        children.add(new Child(8L, "Hannah Lewis", 5, "Female"));
        children.add(new Child(9L, "Ian Walker", 11, "Male"));
        children.add(new Child(10L, "Julia Hall", 7, "Female"));
        children.add(new Child(11L, "Kevin Allen", 6, "Male"));
        children.add(new Child(12L, "Lily Young", 8, "Female"));
        children.add(new Child(13L, "Mason King", 5, "Male"));
        children.add(new Child(14L, "Nora Wright", 9, "Female"));
        children.add(new Child(15L, "Oliver Scott", 12, "Male"));
        children.add(new Child(16L, "Penelope Green", 4, "Female"));
        children.add(new Child(17L, "Quinn Adams", 10, "Male"));
        children.add(new Child(18L, "Ruby Baker", 6, "Female"));
        children.add(new Child(19L, "Samuel Nelson", 8, "Male"));
        children.add(new Child(20L, "Tara Carter", 7, "Female"));
        children.add(new Child(21L, "Uma Mitchell", 5, "Female"));
        children.add(new Child(22L, "Victor Perez", 11, "Male"));
        children.add(new Child(23L, "Willow Roberts", 9, "Female"));
        children.add(new Child(24L, "Xavier Turner", 6, "Male"));
        children.add(new Child(25L, "Yasmin Phillips", 8, "Female"));
        children.add(new Child(26L, "Zachary Campbell", 10, "Male"));
        children.add(new Child(27L, "Amelia Parker", 7, "Female"));
        children.add(new Child(28L, "Benjamin Evans", 5, "Male"));
        children.add(new Child(29L, "Chloe Edwards", 6, "Female"));
        children.add(new Child(30L, "Daniel Collins", 9, "Male"));
        children.add(new Child(31L, "Emma Stewart", 8, "Female"));
        children.add(new Child(32L, "Felix Sanchez", 12, "Male"));
        children.add(new Child(33L, "Grace Morris", 4, "Female"));
        children.add(new Child(34L, "Henry Rogers", 10, "Male"));
        children.add(new Child(35L, "Isla Reed", 7, "Female"));
        children.add(new Child(36L, "Jack Cook", 6, "Male"));
        children.add(new Child(37L, "Kira Morgan", 9, "Female"));
        children.add(new Child(38L, "Leo Bell", 5, "Male"));
        children.add(new Child(39L, "Mia Murphy", 8, "Female"));
        children.add(new Child(40L, "Noah Bailey", 11, "Male"));
    }

    public ChildPageResponse getChildren(int page, String sortBy, int limit) {
        List<Child> sortedList = new ArrayList<>(children);

        Comparator<Child> comparator;
        String sort = (sortBy == null) ? "name" : sortBy.trim();

        switch (sort) {
            case "name-desc" -> comparator = Comparator.comparing(Child::getName, String.CASE_INSENSITIVE_ORDER).reversed();
            case "age", "age-asc" -> comparator = Comparator.comparingInt(Child::getAge);
            case "age-desc" -> comparator = Comparator.comparingInt(Child::getAge).reversed();
            case "id-asc" -> comparator = Comparator.comparingLong(Child::getId);
            case "id-desc" -> comparator = Comparator.comparingLong(Child::getId).reversed();
            case "gender-female" -> comparator = Comparator.comparing(Child::getGender, String.CASE_INSENSITIVE_ORDER)
                    .thenComparing(Child::getName, String.CASE_INSENSITIVE_ORDER);
            case "gender-male" -> comparator = Comparator.comparing(Child::getGender, String.CASE_INSENSITIVE_ORDER).reversed()
                    .thenComparing(Child::getName, String.CASE_INSENSITIVE_ORDER);
            case "name", "name-asc" -> comparator = Comparator.comparing(Child::getName, String.CASE_INSENSITIVE_ORDER);
            default -> comparator = Comparator.comparing(Child::getName, String.CASE_INSENSITIVE_ORDER);
        }

        sortedList.sort(comparator);

        int totalRecords = sortedList.size();
        int totalPages = (int) Math.ceil((double) totalRecords / limit);
        if (totalPages < 1) {
            totalPages = 1;
        }

        int validPage = Math.min(Math.max(page, 1), totalPages);
        int startIndex = (validPage - 1) * limit;
        int endIndex = Math.min(startIndex + limit, totalRecords);

        List<Child> paged = (startIndex < totalRecords)
                ? sortedList.subList(startIndex, endIndex)
                : List.of();

        return new ChildPageResponse(paged, validPage, totalPages, totalRecords);
    }

    public Child addChild(ChildCreateRequest request) {
        Child child = new Child(
                nextId.getAndIncrement(),
                request.getName().trim(),
                request.getAge(),
                request.getGender()
        );
        children.add(child);
        return child;
    }
}
