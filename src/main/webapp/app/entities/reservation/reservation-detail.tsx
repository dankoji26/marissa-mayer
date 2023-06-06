import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './reservation.reducer';

export const ReservationDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const reservationEntity = useAppSelector(state => state.reservation.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="reservationDetailsHeading">
          <Translate contentKey="marissamayerApp.reservation.detail.title">Reservation</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{reservationEntity.id}</dd>
          <dt>
            <span id="statuts">
              <Translate contentKey="marissamayerApp.reservation.statuts">Statuts</Translate>
            </span>
          </dt>
          <dd>{reservationEntity.statuts}</dd>
          <dt>
            <span id="date">
              <Translate contentKey="marissamayerApp.reservation.date">Date</Translate>
            </span>
          </dt>
          <dd>{reservationEntity.date ? <TextFormat value={reservationEntity.date} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <Translate contentKey="marissamayerApp.reservation.user">User</Translate>
          </dt>
          <dd>{reservationEntity.user ? reservationEntity.user.id : ''}</dd>
          <dt>
            <Translate contentKey="marissamayerApp.reservation.cours">Cours</Translate>
          </dt>
          <dd>{reservationEntity.cours ? reservationEntity.cours.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/reservation" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/reservation/${reservationEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ReservationDetail;
