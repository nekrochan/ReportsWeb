package org.example.service.interfaces;

import org.example.models.Host;

public interface HostService {
    void addHost(String hostName);
    Host findHostByHostName(String hostName);
}
