package org.example.service.interfaces;

import org.example.models.Founder;
import org.example.models.Host;
import org.example.service.dto.HostDto;

import java.util.List;

public interface HostService {
    void addHost(String hostName);
    Host findHostByHostName(String hostName);
    List<HostDto> findAllHosts();
    Host updateHost(String hostName);
    void deleteHost(String hostName);
}
