package org.example.models;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "hosts")
public class Host extends BaseEntity{
    private String hostName;
    private Set<Conference> conferences;
    private String id;

    public Host() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "hostName")
    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    @OneToMany(mappedBy = "host")
    public Set<Conference> getConferences() {
        return conferences;
    }

    public void setConferences(Set<Conference> conferences) {
        this.conferences = conferences;
    }
}
