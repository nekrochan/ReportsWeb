package org.example.models;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "founders")
public class Founder extends BaseEntity{
    private String founderName;
    private Set<Conference> conferences;
    private String id;

    public Founder() {
    }

    /*
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

     */

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
