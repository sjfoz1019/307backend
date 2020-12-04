package com.csc.adbackend;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CampaignRepo extends CrudRepository<Campaign, Integer> {
   Optional<Campaign> findById(Integer id);
}
