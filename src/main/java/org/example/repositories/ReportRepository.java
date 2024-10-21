package org.example.repositories;

import org.example.models.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, String> {
    Report findByTheme(String theme);

    List<Report> findAll();
}
