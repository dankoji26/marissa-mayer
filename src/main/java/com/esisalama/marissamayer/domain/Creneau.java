package com.esisalama.marissamayer.domain;

import com.esisalama.marissamayer.domain.enumeration.CreneauStatuts;
import com.esisalama.marissamayer.domain.enumeration.Jour;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Creneau.
 */
@Entity
@Table(name = "creneau")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Creneau implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "jour")
    private Jour jour;

    @Pattern(regexp = "^([0-2]\\d|1[0-9]|2[0-3]):[0-5]\\d$")
    @Column(name = "heure_debut")
    private String heureDebut;

    @Pattern(regexp = "^([0-2]\\d|1[0-9]|2[0-3]):[0-5]\\d$")
    @Column(name = "heure_fin")
    private String heureFin;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "statuts", nullable = false)
    private CreneauStatuts statuts;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "evaluations", "creneaus", "reservations", "categories", "catalogue" }, allowSetters = true)
    private Cours cours;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "instance", "reservations", "evaluations", "creneaus" }, allowSetters = true)
    private Utilisateur user;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Creneau id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Jour getJour() {
        return this.jour;
    }

    public Creneau jour(Jour jour) {
        this.setJour(jour);
        return this;
    }

    public void setJour(Jour jour) {
        this.jour = jour;
    }

    public String getHeureDebut() {
        return this.heureDebut;
    }

    public Creneau heureDebut(String heureDebut) {
        this.setHeureDebut(heureDebut);
        return this;
    }

    public void setHeureDebut(String heureDebut) {
        this.heureDebut = heureDebut;
    }

    public String getHeureFin() {
        return this.heureFin;
    }

    public Creneau heureFin(String heureFin) {
        this.setHeureFin(heureFin);
        return this;
    }

    public void setHeureFin(String heureFin) {
        this.heureFin = heureFin;
    }

    public CreneauStatuts getStatuts() {
        return this.statuts;
    }

    public Creneau statuts(CreneauStatuts statuts) {
        this.setStatuts(statuts);
        return this;
    }

    public void setStatuts(CreneauStatuts statuts) {
        this.statuts = statuts;
    }

    public Cours getCours() {
        return this.cours;
    }

    public void setCours(Cours cours) {
        this.cours = cours;
    }

    public Creneau cours(Cours cours) {
        this.setCours(cours);
        return this;
    }

    public Utilisateur getUser() {
        return this.user;
    }

    public void setUser(Utilisateur utilisateur) {
        this.user = utilisateur;
    }

    public Creneau user(Utilisateur utilisateur) {
        this.setUser(utilisateur);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Creneau)) {
            return false;
        }
        return id != null && id.equals(((Creneau) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Creneau{" +
            "id=" + getId() +
            ", jour='" + getJour() + "'" +
            ", heureDebut='" + getHeureDebut() + "'" +
            ", heureFin='" + getHeureFin() + "'" +
            ", statuts='" + getStatuts() + "'" +
            "}";
    }
}
