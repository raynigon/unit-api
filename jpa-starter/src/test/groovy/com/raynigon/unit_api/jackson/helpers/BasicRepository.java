package com.raynigon.unit_api.jackson.helpers;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BasicRepository extends JpaRepository<BasicEntity, String> {
}
