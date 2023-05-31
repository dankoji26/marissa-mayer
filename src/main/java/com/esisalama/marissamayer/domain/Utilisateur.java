package com.esisalama.marissamayer.domain;

import com.esisalama.marissamayer.domain.enumeration.Role;
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

    @NotNull
    @Column(name = "nom", nullable = false)
    private String nom;

    @NotNull
    @Column(name = "prenom", nullable = false)
    private String prenom;

    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

    @NotNull
    @Column(name = "password", nullable = false)
    private String password;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    @OneToMany(mappedBy = "utilisateur")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "utilisateur" }, allowSetters = true)
    private Set<Reservation> reservations = new HashSet<>();

    @OneToMany(mappedBy = "utilisateur")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "cours", "utilisateur" }, allowSetters = true)
    private Set<Evaluation> evaluations = new HashSet<>();

    @OneToMany(mappedBy = "utilisateur")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "utilisateur" }, allowSetters = true)
    private Set<Notification> notifications = new HashSet<>();

    @OneToMany(mappedBy = "utilisateur")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "cours", "utilisateur" }, allowSetters = true)
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

    public String getNom() {
        return this.nom;
    }

    public Utilisateur nom(String nom) {
        this.setNom(nom);
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return this.prenom;
    }

    public Utilisateur prenom(String prenom) {
        this.setPrenom(prenom);
        return this;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return this.email;
    }

    public Utilisateur email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public Utilisateur password(String password) {
        this.setPassword(password);
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public Utilisateur createdAt(Instant createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Role getRole() {
        return this.role;
    }

    public Utilisateur role(Role role) {
        this.setRole(role);
        return this;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Set<Reservation> getReservations() {
        return this.reservations;
    }

    public void setReservations(Set<Reservation> reservations) {
        if (this.reservations != null) {
            this.reservations.forEach(i -> i.setUtilisateur(null));
        }
        if (reservations != null) {
            reservations.forEach(i -> i.setUtilisateur(this));
        }
        this.reservations = reservations;
    }

    public Utilisateur reservations(Set<Reservation> reservations) {
        this.setReservations(reservations);
        return this;
    }

    public Utilisateur addReservation(Reservation reservation) {
        this.reservations.add(reservation);
        reservation.setUtilisateur(this);
        return this;
    }

    public Utilisateur removeReservation(Reservation reservation) {
        this.reservations.remove(reservation);
        reservation.setUtilisateur(null);
        return this;
    }

    public Set<Evaluation> getEvaluations() {
        return this.evaluations;
    }

    public void setEvaluations(Set<Evaluation> evaluations) {
        if (this.evaluations != null) {
            this.evaluations.forEach(i -> i.setUtilisateur(null));
        }
        if (evaluations != null) {
            evaluations.forEach(i -> i.setUtilisateur(this));
        }
        this.evaluations = evaluations;
    }

    public Utilisateur evaluations(Set<Evaluation> evaluations) {
        this.setEvaluations(evaluations);
        return this;
    }

    public Utilisateur addEvaluation(Evaluation evaluation) {
        this.evaluations.add(evaluation);
        evaluation.setUtilisateur(this);
        return this;
    }

    public Utilisateur removeEvaluation(Evaluation evaluation) {
        this.evaluations.remove(evaluation);
        evaluation.setUtilisateur(null);
        return this;
    }

    public Set<Notification> getNotifications() {
        return this.notifications;
    }

    public void setNotifications(Set<Notification> notifications) {
        if (this.notifications != null) {
            this.notifications.forEach(i -> i.setUtilisateur(null));
        }
        if (notifications != null) {
            notifications.forEach(i -> i.setUtilisateur(this));
        }
        this.notifications = notifications;
    }

    public Utilisateur notifications(Set<Notification> notifications) {
        this.setNotifications(notifications);
        return this;
    }

    public Utilisateur addNotification(Notification notification) {
        this.notifications.add(notification);
        notification.setUtilisateur(this);
        return this;
    }

    public Utilisateur removeNotification(Notification notification) {
        this.notifications.remove(notification);
        notification.setUtilisateur(null);
        return this;
    }

    public Set<Creneau> getCreneaus() {
        return this.creneaus;
    }

    public void setCreneaus(Set<Creneau> creneaus) {
        if (this.creneaus != null) {
            this.creneaus.forEach(i -> i.setUtilisateur(null));
        }
        if (creneaus != null) {
            creneaus.forEach(i -> i.setUtilisateur(this));
        }
        this.creneaus = creneaus;
    }

    public Utilisateur creneaus(Set<Creneau> creneaus) {
        this.setCreneaus(creneaus);
        return this;
    }

    public Utilisateur addCreneau(Creneau creneau) {
        this.creneaus.add(creneau);
        creneau.setUtilisateur(this);
        return this;
    }

    public Utilisateur removeCreneau(Creneau creneau) {
        this.creneaus.remove(creneau);
        creneau.setUtilisateur(null);
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
            ", nom='" + getNom() + "'" +
            ", prenom='" + getPrenom() + "'" +
            ", email='" + getEmail() + "'" +
            ", password='" + getPassword() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", role='" + getRole() + "'" +
            "}";
    }
}
