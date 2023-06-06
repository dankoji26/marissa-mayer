import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './catalogue.reducer';

export const CatalogueDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const catalogueEntity = useAppSelector(state => state.catalogue.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="catalogueDetailsHeading">
          <Translate contentKey="marissamayerApp.catalogue.detail.title">Catalogue</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{catalogueEntity.id}</dd>
          <dt>
            <span id="nom">
              <Translate contentKey="marissamayerApp.catalogue.nom">Nom</Translate>
            </span>
          </dt>
          <dd>{catalogueEntity.nom}</dd>
          <dt>
            <Translate contentKey="marissamayerApp.catalogue.user">User</Translate>
          </dt>
          <dd>{catalogueEntity.user ? catalogueEntity.user.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/catalogue" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/catalogue/${catalogueEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CatalogueDetail;
