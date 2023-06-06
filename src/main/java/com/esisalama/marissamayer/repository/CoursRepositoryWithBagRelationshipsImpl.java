package com.esisalama.marissamayer.repository;

import com.esisalama.marissamayer.domain.Cours;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.annotations.QueryHints;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class CoursRepositoryWithBagRelationshipsImpl implements CoursRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Cours> fetchBagRelationships(Optional<Cours> cours) {
        return cours.map(this::fetchCategories);
    }

    @Override
    public Page<Cours> fetchBagRelationships(Page<Cours> cours) {
        return new PageImpl<>(fetchBagRelationships(cours.getContent()), cours.getPageable(), cours.getTotalElements());
    }

    @Override
    public List<Cours> fetchBagRelationships(List<Cours> cours) {
        return Optional.of(cours).map(this::fetchCategories).orElse(Collections.emptyList());
    }

    Cours fetchCategories(Cours result) {
        return entityManager
            .createQuery("select cours from Cours cours left join fetch cours.categories where cours is :cours", Cours.class)
            .setParameter("cours", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<Cours> fetchCategories(List<Cours> cours) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, cours.size()).forEach(index -> order.put(cours.get(index).getId(), index));
        List<Cours> result = entityManager
            .createQuery("select distinct cours from Cours cours left join fetch cours.categories where cours in :cours", Cours.class)
            .setParameter("cours", cours)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
