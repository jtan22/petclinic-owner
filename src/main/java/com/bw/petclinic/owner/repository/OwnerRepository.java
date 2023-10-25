package com.bw.petclinic.owner.repository;

import com.bw.petclinic.owner.domain.Owner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OwnerRepository extends CrudRepository<Owner, Integer>, PagingAndSortingRepository<Owner, Integer> {

    @Query("select owner from Owner owner where owner.lastName like :lastName%")
    Page<Owner> findPageByLastName(String lastName, Pageable pageable);

}
