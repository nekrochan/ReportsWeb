package org.example.service.interfaces;

import org.example.models.Founder;
import org.example.service.dto.FounderDto;

import java.util.List;

public interface FounderService {
    void addFounder(String founderName);
    Founder findFounderByFounderName(String founderName);
    List<FounderDto> findAllFounders();
    Founder updateFounder(String founderName);
    void deleteFounder(String founderName);
}
