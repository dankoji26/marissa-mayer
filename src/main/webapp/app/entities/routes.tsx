import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Utilisateur from './utilisateur';
import Reservation from './reservation';
import Categorie from './categorie';
import Cours from './cours';
import Paiement from './paiement';
import Catalogue from './catalogue';
import Notification from './notification';
import Evaluation from './evaluation';
import Creneau from './creneau';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="utilisateur/*" element={<Utilisateur />} />
        <Route path="reservation/*" element={<Reservation />} />
        <Route path="categorie/*" element={<Categorie />} />
        <Route path="cours/*" element={<Cours />} />
        <Route path="paiement/*" element={<Paiement />} />
        <Route path="catalogue/*" element={<Catalogue />} />
        <Route path="notification/*" element={<Notification />} />
        <Route path="evaluation/*" element={<Evaluation />} />
        <Route path="creneau/*" element={<Creneau />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};
