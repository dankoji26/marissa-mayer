import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './evaluation.reducer';

export const EvaluationDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const evaluationEntity = useAppSelector(state => state.evaluation.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="evaluationDetailsHeading">
          <Translate contentKey="marissamayerApp.evaluation.detail.title">Evaluation</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{evaluationEntity.id}</dd>
          <dt>
            <span id="commentaire">
              <Translate contentKey="marissamayerApp.evaluation.commentaire">Commentaire</Translate>
            </span>
          </dt>
          <dd>{evaluationEntity.commentaire}</dd>
          <dt>
            <span id="createdAt">
              <Translate contentKey="marissamayerApp.evaluation.createdAt">Created At</Translate>
            </span>
          </dt>
          <dd>
            {evaluationEntity.createdAt ? <TextFormat value={evaluationEntity.createdAt} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <Translate contentKey="marissamayerApp.evaluation.cours">Cours</Translate>
          </dt>
          <dd>{evaluationEntity.cours ? evaluationEntity.cours.id : ''}</dd>
          <dt>
            <Translate contentKey="marissamayerApp.evaluation.user">User</Translate>
          </dt>
          <dd>{evaluationEntity.user ? evaluationEntity.user.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/evaluation" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/evaluation/${evaluationEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default EvaluationDetail;
