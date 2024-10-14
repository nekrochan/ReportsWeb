package org.example.service.interfaces;

import org.example.models.Founder;

public interface FounderService {
    void addFounder(String founderName);
    Founder findFounderByFounderName(String founderName);
}
