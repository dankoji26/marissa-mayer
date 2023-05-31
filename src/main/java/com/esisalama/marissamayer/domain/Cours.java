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
 * A Cours.
 */
@Entity
@Table(name = "cours")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Cours implements Serializable {

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
    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "duree")
    private String duree;

    @Column(name = "prerequis")
    private String prerequis;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @OneToMany(mappedBy = "cours")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "cours", "utilisateur" }, allowSetters = true)
    private Set<Evaluation> evaluations = new HashSet<>();

    @OneToMany(mappedBy = "cours")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "cours", "utilisateur" }, allowSetters = true)
    private Set<Creneau> creneaus = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "utilisateur", "cours" }, allowSetters = true)
    private Catalogue catalogue;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Cours id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return this.nom;
    }

    public Cours nom(String nom) {
        this.setNom(nom);
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return this.description;
    }

    public Cours description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDuree() {
        return this.duree;
    }

    public Cours duree(String duree) {
        this.setDuree(duree);
        return this;
    }

    public void setDuree(String duree) {
        this.duree = duree;
    }

    public String getPrerequis() {
        return this.prerequis;
    }

    public Cours prerequis(String prerequis) {
        this.setPrerequis(prerequis);
        return this;
    }

    public void setPrerequis(String prerequis) {
        this.prerequis = prerequis;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public Cours createdAt(Instant createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Set<Evaluation> getEvaluations() {
        return this.evaluations;
    }

    public void setEvaluations(Set<Evaluation> evaluations) {
        if (this.evaluations != null) {
            this.evaluations.forEach(i -> i.setCours(null));
        }
        if (evaluations != null) {
            evaluations.forEach(i -> i.setCours(this));
        }
        this.evaluations = evaluations;
    }

    public Cours evaluations(Set<Evaluation> evaluations) {
        this.setEvaluations(evaluations);
        return this;
    }

    public Cours addEvaluation(Evaluation evaluation) {
        this.evaluations.add(evaluation);
        evaluation.setCours(this);
        return this;
    }

    public Cours removeEvaluation(Evaluation evaluation) {
        this.evaluations.remove(evaluation);
        evaluation.setCours(null);
        return this;
    }

    public Set<Creneau> getCreneaus() {
        return this.creneaus;
    }

    public void setCreneaus(Set<Creneau> creneaus) {
        if (this.creneaus != null) {
            this.creneaus.forEach(i -> i.setCours(null));
        }
        if (creneaus != null) {
            creneaus.forEach(i -> i.setCours(this));
        }
        this.creneaus = creneaus;
    }

    public Cours creneaus(Set<Creneau> creneaus) {
        this.setCreneaus(creneaus);
        return this;
    }

    public Cours addCreneau(Creneau creneau) {
        this.creneaus.add(creneau);
        creneau.setCours(this);
        return this;
    }

    public Cours removeCreneau(Creneau creneau) {
        this.creneaus.remove(creneau);
        creneau.setCours(null);
        return this;
    }

    public Catalogue getCatalogue() {
        return this.catalogue;
    }

    public void setCatalogue(Catalogue catalogue) {
        this.catalogue = catalogue;
    }

    public Cours catalogue(Catalogue catalogue) {
        this.setCatalogue(catalogue);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Cours)) {
            return false;
        }
        return id != null && id.equals(((Cours) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Cours{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", description='" + getDescription() + "'" +
            ", duree='" + getDuree() + "'" +
            ", prerequis='" + getPrerequis() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            "}";
    }
}
