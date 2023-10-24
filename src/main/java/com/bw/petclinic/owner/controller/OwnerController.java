package com.bw.petclinic.owner.controller;

import com.bw.petclinic.owner.domain.Owner;
import com.bw.petclinic.owner.domain.PagedOwners;
import com.bw.petclinic.owner.repository.OwnerRepository;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
public class OwnerController {

    private static final Logger LOG = LoggerFactory.getLogger(OwnerController.class);

    @Autowired
    private OwnerRepository ownerRepository;

    @GetMapping("/")
    public String welcome() {
        return "welcome";
    }

    @GetMapping("/owners")
    public Owner getById(@RequestParam("ownerId") int ownerId) {
        LOG.info("GET /owners with ownerId [" + ownerId + "]");
        return ownerRepository.findById(ownerId).orElseGet(Owner::new);
    }

    @PostMapping("/owners")
    public Owner save(@RequestBody Owner owner) {
        LOG.info("POST /owners with owner [" + owner + "]");
        return ownerRepository.save(owner);
    }

    @GetMapping("/owners/find")
    public PagedOwners findByLastName(@RequestParam(value = "lastName", required = false) String lastName,
                                      @RequestParam("pageNumber") int pageNumber,
                                      @RequestParam("pageSize") int pageSize) {
        LOG.info("GET /owners/find with lastName [" + lastName + "], pageNumber [" + pageNumber + "], pageSize [" + pageSize + "]");
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        if (StringUtils.isBlank(lastName)) {
            return new PagedOwners(ownerRepository.findAll(pageable));
        } else {
            return new PagedOwners(ownerRepository.findByLastName(lastName, pageable));
        }
    }

}
