package org.example.repositories;

import org.example.models.Conference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConferenceRepository extends JpaRepository<Conference, String> {
    Conference findConferenceByConfName(String name);

    List<Conference> findAll();
}
