import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './paiement.reducer';

export const PaiementDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const paiementEntity = useAppSelector(state => state.paiement.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="paiementDetailsHeading">
          <Translate contentKey="marissamayerApp.paiement.detail.title">Paiement</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{paiementEntity.id}</dd>
          <dt>
            <span id="montant">
              <Translate contentKey="marissamayerApp.paiement.montant">Montant</Translate>
            </span>
          </dt>
          <dd>{paiementEntity.montant}</dd>
          <dt>
            <Translate contentKey="marissamayerApp.paiement.reservation">Reservation</Translate>
          </dt>
          <dd>{paiementEntity.reservation ? paiementEntity.reservation.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/paiement" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/paiement/${paiementEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default PaiementDetail;
