package org.example.repositories;

import org.example.models.Reporter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReporterRepository extends JpaRepository<Reporter, String> {
    Reporter findReporterByReporterName(String reporterName);

    List<Reporter> findAll();
}
