package com.csc.adbackend;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CampaignRepo extends CrudRepository<Campaign, Integer> {
   Campaign findById(long id);
}
