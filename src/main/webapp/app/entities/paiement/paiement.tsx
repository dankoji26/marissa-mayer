import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IPaiement } from 'app/shared/model/paiement.model';
import { getEntities } from './paiement.reducer';

export const Paiement = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const paiementList = useAppSelector(state => state.paiement.entities);
  const loading = useAppSelector(state => state.paiement.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="paiement-heading" data-cy="PaiementHeading">
        <Translate contentKey="marissamayerApp.paiement.home.title">Paiements</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="marissamayerApp.paiement.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/paiement/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="marissamayerApp.paiement.home.createLabel">Create new Paiement</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {paiementList && paiementList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="marissamayerApp.paiement.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="marissamayerApp.paiement.montant">Montant</Translate>
                </th>
                <th>
                  <Translate contentKey="marissamayerApp.paiement.createdAt">Created At</Translate>
                </th>
                <th>
                  <Translate contentKey="marissamayerApp.paiement.reservation">Reservation</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {paiementList.map((paiement, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/paiement/${paiement.id}`} color="link" size="sm">
                      {paiement.id}
                    </Button>
                  </td>
                  <td>{paiement.montant}</td>
                  <td>{paiement.createdAt ? <TextFormat type="date" value={paiement.createdAt} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>
                    {paiement.reservation ? <Link to={`/reservation/${paiement.reservation.id}`}>{paiement.reservation.id}</Link> : ''}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/paiement/${paiement.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/paiement/${paiement.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/paiement/${paiement.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="marissamayerApp.paiement.home.notFound">No Paiements found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Paiement;
