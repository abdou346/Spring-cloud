package org.sid.billingservice.repository;

import org.sid.billingservice.entities.Productitem;
import org.sid.billingservice.entities.bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Collection;

@RepositoryRestResource
public interface Productitemrepository extends JpaRepository<Productitem,Long> {
    public Collection<Productitem> findByBillId (Long id);
}
