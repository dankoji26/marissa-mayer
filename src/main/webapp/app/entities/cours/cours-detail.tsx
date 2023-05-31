import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './cours.reducer';

export const CoursDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const coursEntity = useAppSelector(state => state.cours.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="coursDetailsHeading">
          <Translate contentKey="marissamayerApp.cours.detail.title">Cours</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{coursEntity.id}</dd>
          <dt>
            <span id="nom">
              <Translate contentKey="marissamayerApp.cours.nom">Nom</Translate>
            </span>
          </dt>
          <dd>{coursEntity.nom}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="marissamayerApp.cours.description">Description</Translate>
            </span>
          </dt>
          <dd>{coursEntity.description}</dd>
          <dt>
            <span id="duree">
              <Translate contentKey="marissamayerApp.cours.duree">Duree</Translate>
            </span>
          </dt>
          <dd>{coursEntity.duree}</dd>
          <dt>
            <span id="prerequis">
              <Translate contentKey="marissamayerApp.cours.prerequis">Prerequis</Translate>
            </span>
          </dt>
          <dd>{coursEntity.prerequis}</dd>
          <dt>
            <span id="createdAt">
              <Translate contentKey="marissamayerApp.cours.createdAt">Created At</Translate>
            </span>
          </dt>
          <dd>{coursEntity.createdAt ? <TextFormat value={coursEntity.createdAt} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <Translate contentKey="marissamayerApp.cours.catalogue">Catalogue</Translate>
          </dt>
          <dd>{coursEntity.catalogue ? coursEntity.catalogue.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/cours" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/cours/${coursEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CoursDetail;
