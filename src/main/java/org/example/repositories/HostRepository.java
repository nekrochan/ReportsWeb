package org.example.repositories;

import org.example.models.Host;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HostRepository extends JpaRepository<Host, String> {
    Host findHostByName(String name);
}
