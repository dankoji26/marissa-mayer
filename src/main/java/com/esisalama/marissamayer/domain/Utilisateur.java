package com.esisalama.marissamayer.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Utilisateur.
 */
@Entity
@Table(name = "utilisateur")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Utilisateur implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private User instance;

    @OneToMany(mappedBy = "user")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "user", "cours" }, allowSetters = true)
    private Set<Reservation> reservations = new HashSet<>();

    @OneToMany(mappedBy = "user")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "cours", "user" }, allowSetters = true)
    private Set<Evaluation> evaluations = new HashSet<>();

    @OneToMany(mappedBy = "user")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "cours", "user" }, allowSetters = true)
    private Set<Creneau> creneaus = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Utilisateur id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getInstance() {
        return this.instance;
    }

    public void setInstance(User user) {
        this.instance = user;
    }

    public Utilisateur instance(User user) {
        this.setInstance(user);
        return this;
    }

    public Set<Reservation> getReservations() {
        return this.reservations;
    }

    public void setReservations(Set<Reservation> reservations) {
        if (this.reservations != null) {
            this.reservations.forEach(i -> i.setUser(null));
        }
        if (reservations != null) {
            reservations.forEach(i -> i.setUser(this));
        }
        this.reservations = reservations;
    }

    public Utilisateur reservations(Set<Reservation> reservations) {
        this.setReservations(reservations);
        return this;
    }

    public Utilisateur addReservation(Reservation reservation) {
        this.reservations.add(reservation);
        reservation.setUser(this);
        return this;
    }

    public Utilisateur removeReservation(Reservation reservation) {
        this.reservations.remove(reservation);
        reservation.setUser(null);
        return this;
    }

    public Set<Evaluation> getEvaluations() {
        return this.evaluations;
    }

    public void setEvaluations(Set<Evaluation> evaluations) {
        if (this.evaluations != null) {
            this.evaluations.forEach(i -> i.setUser(null));
        }
        if (evaluations != null) {
            evaluations.forEach(i -> i.setUser(this));
        }
        this.evaluations = evaluations;
    }

    public Utilisateur evaluations(Set<Evaluation> evaluations) {
        this.setEvaluations(evaluations);
        return this;
    }

    public Utilisateur addEvaluation(Evaluation evaluation) {
        this.evaluations.add(evaluation);
        evaluation.setUser(this);
        return this;
    }

    public Utilisateur removeEvaluation(Evaluation evaluation) {
        this.evaluations.remove(evaluation);
        evaluation.setUser(null);
        return this;
    }

    public Set<Creneau> getCreneaus() {
        return this.creneaus;
    }

    public void setCreneaus(Set<Creneau> creneaus) {
        if (this.creneaus != null) {
            this.creneaus.forEach(i -> i.setUser(null));
        }
        if (creneaus != null) {
            creneaus.forEach(i -> i.setUser(this));
        }
        this.creneaus = creneaus;
    }

    public Utilisateur creneaus(Set<Creneau> creneaus) {
        this.setCreneaus(creneaus);
        return this;
    }

    public Utilisateur addCreneau(Creneau creneau) {
        this.creneaus.add(creneau);
        creneau.setUser(this);
        return this;
    }

    public Utilisateur removeCreneau(Creneau creneau) {
        this.creneaus.remove(creneau);
        creneau.setUser(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Utilisateur)) {
            return false;
        }
        return id != null && id.equals(((Utilisateur) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Utilisateur{" +
            "id=" + getId() +
            "}";
    }
}
