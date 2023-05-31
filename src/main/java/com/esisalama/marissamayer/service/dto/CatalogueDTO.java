package com.esisalama.marissamayer.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.esisalama.marissamayer.domain.Catalogue} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CatalogueDTO implements Serializable {

    private Long id;

    @NotNull
    private String nom;

    @NotNull
    private Instant createdAt;

    private UtilisateurDTO utilisateur;

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

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public UtilisateurDTO getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(UtilisateurDTO utilisateur) {
        this.utilisateur = utilisateur;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CatalogueDTO)) {
            return false;
        }

        CatalogueDTO catalogueDTO = (CatalogueDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, catalogueDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CatalogueDTO{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", utilisateur=" + getUtilisateur() +
            "}";
    }
}
