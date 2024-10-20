package org.example.repositories;

import org.example.models.Host;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HostRepository extends JpaRepository<Host, String> {
    Host findHostByHostName(String name);

}
