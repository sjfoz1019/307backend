package com.csc.adbackend;

import org.springframework.data.repository.CrudRepository;


interface CampaignRepo extends CrudRepository<Campaign, Integer> {
    Campaign findById(long id);
}

