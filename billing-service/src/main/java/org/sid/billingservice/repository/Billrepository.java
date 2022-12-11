package org.sid.billingservice.repository;

import org.sid.billingservice.entities.bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = {"http://localhost:4200"})
@RepositoryRestResource
public interface Billrepository extends JpaRepository<bill,Long> {
}
