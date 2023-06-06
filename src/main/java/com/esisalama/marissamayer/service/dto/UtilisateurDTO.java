package com.esisalama.marissamayer.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.esisalama.marissamayer.domain.Utilisateur} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class UtilisateurDTO implements Serializable {

    private Long id;

    private UserDTO instance;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserDTO getInstance() {
        return instance;
    }

    public void setInstance(UserDTO instance) {
        this.instance = instance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UtilisateurDTO)) {
            return false;
        }

        UtilisateurDTO utilisateurDTO = (UtilisateurDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, utilisateurDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UtilisateurDTO{" +
            "id=" + getId() +
            ", instance=" + getInstance() +
            "}";
    }
}
