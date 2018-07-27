package com.hnyp.axon.vport.rest.repositories;

import com.hnyp.axon.vport.rest.domain.BaseVport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VportRepository extends JpaRepository<BaseVport, String> {

}
