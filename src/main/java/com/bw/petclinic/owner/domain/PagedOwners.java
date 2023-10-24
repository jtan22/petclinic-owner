package com.bw.petclinic.owner.domain;

import org.springframework.data.domain.Page;

import java.util.List;

public class PagedOwners {

    private List<Owner> owners;
    private int pageNumber;
    private int pageSize;
    private int totalPages;
    private long totalOwners;

    public PagedOwners(Page<Owner> owners) {
        this.owners = owners.getContent();
        this.pageNumber = owners.getNumber();
        this.pageSize = owners.getSize();
        this.totalPages = owners.getTotalPages();
        this.totalOwners = owners.getTotalElements();
    }

    public List<Owner> getOwners() {
        return owners;
    }

    public void setOwners(List<Owner> owners) {
        this.owners = owners;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public long getTotalOwners() {
        return totalOwners;
    }

    public void setTotalOwners(long totalOwners) {
        this.totalOwners = totalOwners;
    }

    @Override
    public String toString() {
        return "PagedOwners{" +
                "owners=" + owners +
                ", pageNumber=" + pageNumber +
                ", pageSize=" + pageSize +
                ", totalPages=" + totalPages +
                ", totalOwners=" + totalOwners +
                '}';
    }
}
