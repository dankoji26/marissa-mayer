package com.esisalama.marissamayer.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
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

    private Integer duree;

    private String prerequis;

    private Long prix;

    private Set<CategorieDTO> categories = new HashSet<>();

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

    public Integer getDuree() {
        return duree;
    }

    public void setDuree(Integer duree) {
        this.duree = duree;
    }

    public String getPrerequis() {
        return prerequis;
    }

    public void setPrerequis(String prerequis) {
        this.prerequis = prerequis;
    }

    public Long getPrix() {
        return prix;
    }

    public void setPrix(Long prix) {
        this.prix = prix;
    }

    public Set<CategorieDTO> getCategories() {
        return categories;
    }

    public void setCategories(Set<CategorieDTO> categories) {
        this.categories = categories;
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
            ", duree=" + getDuree() +
            ", prerequis='" + getPrerequis() + "'" +
            ", prix=" + getPrix() +
            ", categories=" + getCategories() +
            ", catalogue=" + getCatalogue() +
            "}";
    }
}
