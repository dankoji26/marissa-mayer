import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './notification.reducer';

export const NotificationDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const notificationEntity = useAppSelector(state => state.notification.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="notificationDetailsHeading">
          <Translate contentKey="marissamayerApp.notification.detail.title">Notification</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{notificationEntity.id}</dd>
          <dt>
            <span id="message">
              <Translate contentKey="marissamayerApp.notification.message">Message</Translate>
            </span>
          </dt>
          <dd>{notificationEntity.message}</dd>
          <dt>
            <span id="statuts">
              <Translate contentKey="marissamayerApp.notification.statuts">Statuts</Translate>
            </span>
          </dt>
          <dd>{notificationEntity.statuts}</dd>
          <dt>
            <span id="createdAt">
              <Translate contentKey="marissamayerApp.notification.createdAt">Created At</Translate>
            </span>
          </dt>
          <dd>
            {notificationEntity.createdAt ? <TextFormat value={notificationEntity.createdAt} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <Translate contentKey="marissamayerApp.notification.utilisateur">Utilisateur</Translate>
          </dt>
          <dd>{notificationEntity.utilisateur ? notificationEntity.utilisateur.email : ''}</dd>
        </dl>
        <Button tag={Link} to="/notification" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/notification/${notificationEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default NotificationDetail;
