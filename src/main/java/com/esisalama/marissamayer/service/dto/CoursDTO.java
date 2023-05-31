package com.esisalama.marissamayer.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.esisalama.marissamayer.domain.Cours} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CoursDTO implements Serializable {

    private Long id;

    @NotNull
    private String nom;

    @NotNull
    private String description;

    private String duree;

    private String prerequis;

    @NotNull
    private Instant createdAt;

    private CatalogueDTO catalogue;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDuree() {
        return duree;
    }

    public void setDuree(String duree) {
        this.duree = duree;
    }

    public String getPrerequis() {
        return prerequis;
    }

    public void setPrerequis(String prerequis) {
        this.prerequis = prerequis;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public CatalogueDTO getCatalogue() {
        return catalogue;
    }

    public void setCatalogue(CatalogueDTO catalogue) {
        this.catalogue = catalogue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CoursDTO)) {
            return false;
        }

        CoursDTO coursDTO = (CoursDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, coursDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CoursDTO{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", description='" + getDescription() + "'" +
            ", duree='" + getDuree() + "'" +
            ", prerequis='" + getPrerequis() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", catalogue=" + getCatalogue() +
            "}";
    }
}
