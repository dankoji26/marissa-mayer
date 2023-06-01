import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './creneau.reducer';

export const CreneauDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const creneauEntity = useAppSelector(state => state.creneau.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="creneauDetailsHeading">
          <Translate contentKey="marissamayerApp.creneau.detail.title">Creneau</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{creneauEntity.id}</dd>
          <dt>
            <span id="jour">
              <Translate contentKey="marissamayerApp.creneau.jour">Jour</Translate>
            </span>
          </dt>
          <dd>{creneauEntity.jour}</dd>
          <dt>
            <span id="heureDebut">
              <Translate contentKey="marissamayerApp.creneau.heureDebut">Heure Debut</Translate>
            </span>
          </dt>
          <dd>{creneauEntity.heureDebut}</dd>
          <dt>
            <span id="heureFin">
              <Translate contentKey="marissamayerApp.creneau.heureFin">Heure Fin</Translate>
            </span>
          </dt>
          <dd>{creneauEntity.heureFin}</dd>
          <dt>
            <span id="statuts">
              <Translate contentKey="marissamayerApp.creneau.statuts">Statuts</Translate>
            </span>
          </dt>
          <dd>{creneauEntity.statuts}</dd>
          <dt>
            <Translate contentKey="marissamayerApp.creneau.cours">Cours</Translate>
          </dt>
          <dd>{creneauEntity.cours ? creneauEntity.cours.nom : ''}</dd>
          <dt>
            <Translate contentKey="marissamayerApp.creneau.utilisateur">Utilisateur</Translate>
          </dt>
          <dd>{creneauEntity.utilisateur ? creneauEntity.utilisateur.email : ''}</dd>
        </dl>
        <Button tag={Link} to="/creneau" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/creneau/${creneauEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CreneauDetail;
