package org.example.repositories;

import org.example.models.Founder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FounderRepository extends JpaRepository<Founder, String> {
    Founder findFounderByFounderName(String name);
}
