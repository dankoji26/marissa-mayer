package com.esisalama.marissamayer.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Catalogue.
 */
@Entity
@Table(name = "catalogue")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Catalogue implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "nom", nullable = false)
    private String nom;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @JsonIgnoreProperties(value = { "reservations", "evaluations", "notifications", "creneaus" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Utilisateur utilisateur;

    @OneToMany(mappedBy = "catalogue")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "evaluations", "creneaus", "catalogue" }, allowSetters = true)
    private Set<Cours> cours = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Catalogue id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return this.nom;
    }

    public Catalogue nom(String nom) {
        this.setNom(nom);
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public Catalogue createdAt(Instant createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Utilisateur getUtilisateur() {
        return this.utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Catalogue utilisateur(Utilisateur utilisateur) {
        this.setUtilisateur(utilisateur);
        return this;
    }

    public Set<Cours> getCours() {
        return this.cours;
    }

    public void setCours(Set<Cours> cours) {
        if (this.cours != null) {
            this.cours.forEach(i -> i.setCatalogue(null));
        }
        if (cours != null) {
            cours.forEach(i -> i.setCatalogue(this));
        }
        this.cours = cours;
    }

    public Catalogue cours(Set<Cours> cours) {
        this.setCours(cours);
        return this;
    }

    public Catalogue addCours(Cours cours) {
        this.cours.add(cours);
        cours.setCatalogue(this);
        return this;
    }

    public Catalogue removeCours(Cours cours) {
        this.cours.remove(cours);
        cours.setCatalogue(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Catalogue)) {
            return false;
        }
        return id != null && id.equals(((Catalogue) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Catalogue{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            "}";
    }
}
