import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ICreneau } from 'app/shared/model/creneau.model';
import { getEntities } from './creneau.reducer';

export const Creneau = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const creneauList = useAppSelector(state => state.creneau.entities);
  const loading = useAppSelector(state => state.creneau.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="creneau-heading" data-cy="CreneauHeading">
        <Translate contentKey="marissamayerApp.creneau.home.title">Creneaus</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="marissamayerApp.creneau.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/creneau/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="marissamayerApp.creneau.home.createLabel">Create new Creneau</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {creneauList && creneauList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="marissamayerApp.creneau.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="marissamayerApp.creneau.jour">Jour</Translate>
                </th>
                <th>
                  <Translate contentKey="marissamayerApp.creneau.heureDebut">Heure Debut</Translate>
                </th>
                <th>
                  <Translate contentKey="marissamayerApp.creneau.heureFin">Heure Fin</Translate>
                </th>
                <th>
                  <Translate contentKey="marissamayerApp.creneau.statuts">Statuts</Translate>
                </th>
                <th>
                  <Translate contentKey="marissamayerApp.creneau.cours">Cours</Translate>
                </th>
                <th>
                  <Translate contentKey="marissamayerApp.creneau.utilisateur">Utilisateur</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {creneauList.map((creneau, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/creneau/${creneau.id}`} color="link" size="sm">
                      {creneau.id}
                    </Button>
                  </td>
                  <td>
                    <Translate contentKey={`marissamayerApp.Jour.${creneau.jour}`} />
                  </td>
                  <td>{creneau.heureDebut}</td>
                  <td>{creneau.heureFin}</td>
                  <td>
                    <Translate contentKey={`marissamayerApp.CreneauStatuts.${creneau.statuts}`} />
                  </td>
                  <td>{creneau.cours ? <Link to={`/cours/${creneau.cours.id}`}>{creneau.cours.nom}</Link> : ''}</td>
                  <td>{creneau.utilisateur ? <Link to={`/utilisateur/${creneau.utilisateur.id}`}>{creneau.utilisateur.email}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/creneau/${creneau.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/creneau/${creneau.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/creneau/${creneau.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="marissamayerApp.creneau.home.notFound">No Creneaus found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Creneau;
