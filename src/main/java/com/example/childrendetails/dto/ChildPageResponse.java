package com.example.childrendetails.dto;

import com.example.childrendetails.model.Child;
import java.util.List;

public class ChildPageResponse {
    private List<Child> children;
    private int currentPage;
    private int totalPages;
    private long totalRecords;

    public ChildPageResponse() {
    }

    public ChildPageResponse(List<Child> children, int currentPage, int totalPages, long totalRecords) {
        this.children = children;
        this.currentPage = currentPage;
        this.totalPages = totalPages;
        this.totalRecords = totalRecords;
    }

    public List<Child> getChildren() {
        return children;
    }

    public void setChildren(List<Child> children) {
        this.children = children;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public long getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(long totalRecords) {
        this.totalRecords = totalRecords;
    }
}
