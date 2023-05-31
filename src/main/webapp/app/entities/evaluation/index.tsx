import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Evaluation from './evaluation';
import EvaluationDetail from './evaluation-detail';
import EvaluationUpdate from './evaluation-update';
import EvaluationDeleteDialog from './evaluation-delete-dialog';

const EvaluationRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Evaluation />} />
    <Route path="new" element={<EvaluationUpdate />} />
    <Route path=":id">
      <Route index element={<EvaluationDetail />} />
      <Route path="edit" element={<EvaluationUpdate />} />
      <Route path="delete" element={<EvaluationDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default EvaluationRoutes;
