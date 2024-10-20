package org.example.service.interfaces;

import org.example.models.Host;

import java.util.List;

public interface HostService {
    void addHost(String hostName);
    Host findHostByHostName(String hostName);
}
