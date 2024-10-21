package org.example.repositories;

import org.example.models.Founder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FounderRepository extends JpaRepository<Founder, String> {
    Founder findFounderByFounderName(String name);

    List<Founder> findAll();
}
