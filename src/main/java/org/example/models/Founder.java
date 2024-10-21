package org.example.models;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "founders")
public class Founder extends BaseEntity{
    private String founderName;
    private Set<Conference> conferences;

    public Founder() {
    }

    public String getFounderName() {
        return founderName;
    }

    public void setFounderName(String founderName) {
        this.founderName = founderName;
    }

    @OneToMany(mappedBy = "founder")
    public Set<Conference> getConferences() {
        return conferences;
    }

    public void setConferences(Set<Conference> conferences) {
        this.conferences = conferences;
    }
}
