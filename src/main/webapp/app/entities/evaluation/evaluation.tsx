import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IEvaluation } from 'app/shared/model/evaluation.model';
import { getEntities } from './evaluation.reducer';

export const Evaluation = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const evaluationList = useAppSelector(state => state.evaluation.entities);
  const loading = useAppSelector(state => state.evaluation.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="evaluation-heading" data-cy="EvaluationHeading">
        <Translate contentKey="marissamayerApp.evaluation.home.title">Evaluations</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="marissamayerApp.evaluation.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/evaluation/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="marissamayerApp.evaluation.home.createLabel">Create new Evaluation</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {evaluationList && evaluationList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="marissamayerApp.evaluation.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="marissamayerApp.evaluation.commentaire">Commentaire</Translate>
                </th>
                <th>
                  <Translate contentKey="marissamayerApp.evaluation.createdAt">Created At</Translate>
                </th>
                <th>
                  <Translate contentKey="marissamayerApp.evaluation.cours">Cours</Translate>
                </th>
                <th>
                  <Translate contentKey="marissamayerApp.evaluation.user">User</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {evaluationList.map((evaluation, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/evaluation/${evaluation.id}`} color="link" size="sm">
                      {evaluation.id}
                    </Button>
                  </td>
                  <td>{evaluation.commentaire}</td>
                  <td>{evaluation.createdAt ? <TextFormat type="date" value={evaluation.createdAt} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{evaluation.cours ? <Link to={`/cours/${evaluation.cours.id}`}>{evaluation.cours.id}</Link> : ''}</td>
                  <td>{evaluation.user ? <Link to={`/utilisateur/${evaluation.user.id}`}>{evaluation.user.id}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/evaluation/${evaluation.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/evaluation/${evaluation.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/evaluation/${evaluation.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="marissamayerApp.evaluation.home.notFound">No Evaluations found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Evaluation;
