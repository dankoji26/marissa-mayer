import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Cours from './cours';
import CoursDetail from './cours-detail';
import CoursUpdate from './cours-update';
import CoursDeleteDialog from './cours-delete-dialog';

const CoursRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Cours />} />
    <Route path="new" element={<CoursUpdate />} />
    <Route path=":id">
      <Route index element={<CoursDetail />} />
      <Route path="edit" element={<CoursUpdate />} />
      <Route path="delete" element={<CoursDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default CoursRoutes;
