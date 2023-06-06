import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ICatalogue } from 'app/shared/model/catalogue.model';
import { getEntities } from './catalogue.reducer';

export const Catalogue = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const catalogueList = useAppSelector(state => state.catalogue.entities);
  const loading = useAppSelector(state => state.catalogue.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="catalogue-heading" data-cy="CatalogueHeading">
        <Translate contentKey="marissamayerApp.catalogue.home.title">Catalogues</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="marissamayerApp.catalogue.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/catalogue/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="marissamayerApp.catalogue.home.createLabel">Create new Catalogue</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {catalogueList && catalogueList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="marissamayerApp.catalogue.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="marissamayerApp.catalogue.nom">Nom</Translate>
                </th>
                <th>
                  <Translate contentKey="marissamayerApp.catalogue.user">User</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {catalogueList.map((catalogue, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/catalogue/${catalogue.id}`} color="link" size="sm">
                      {catalogue.id}
                    </Button>
                  </td>
                  <td>{catalogue.nom}</td>
                  <td>{catalogue.user ? catalogue.user.id : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/catalogue/${catalogue.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/catalogue/${catalogue.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/catalogue/${catalogue.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="marissamayerApp.catalogue.home.notFound">No Catalogues found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Catalogue;
