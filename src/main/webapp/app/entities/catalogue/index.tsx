import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Catalogue from './catalogue';
import CatalogueDetail from './catalogue-detail';
import CatalogueUpdate from './catalogue-update';
import CatalogueDeleteDialog from './catalogue-delete-dialog';

const CatalogueRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Catalogue />} />
    <Route path="new" element={<CatalogueUpdate />} />
    <Route path=":id">
      <Route index element={<CatalogueDetail />} />
      <Route path="edit" element={<CatalogueUpdate />} />
      <Route path="delete" element={<CatalogueDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default CatalogueRoutes;
