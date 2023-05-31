import utilisateur from 'app/entities/utilisateur/utilisateur.reducer';
import reservation from 'app/entities/reservation/reservation.reducer';
import categorie from 'app/entities/categorie/categorie.reducer';
import cours from 'app/entities/cours/cours.reducer';
import paiement from 'app/entities/paiement/paiement.reducer';
import catalogue from 'app/entities/catalogue/catalogue.reducer';
import notification from 'app/entities/notification/notification.reducer';
import evaluation from 'app/entities/evaluation/evaluation.reducer';
import creneau from 'app/entities/creneau/creneau.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  utilisateur,
  reservation,
  categorie,
  cours,
  paiement,
  catalogue,
  notification,
  evaluation,
  creneau,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
