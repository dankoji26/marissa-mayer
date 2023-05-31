package com.esisalama.marissamayer.repository;

import com.esisalama.marissamayer.domain.Creneau;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Creneau entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CreneauRepository extends JpaRepository<Creneau, Long> {}
