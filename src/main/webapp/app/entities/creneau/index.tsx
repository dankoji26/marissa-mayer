import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Creneau from './creneau';
import CreneauDetail from './creneau-detail';
import CreneauUpdate from './creneau-update';
import CreneauDeleteDialog from './creneau-delete-dialog';

const CreneauRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Creneau />} />
    <Route path="new" element={<CreneauUpdate />} />
    <Route path=":id">
      <Route index element={<CreneauDetail />} />
      <Route path="edit" element={<CreneauUpdate />} />
      <Route path="delete" element={<CreneauDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default CreneauRoutes;
