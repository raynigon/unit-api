package com.raynigon.unit.api.jpa.helpers;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BasicRepository extends JpaRepository<BasicEntity, String> {
}
