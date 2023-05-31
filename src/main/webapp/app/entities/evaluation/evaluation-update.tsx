import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ICours } from 'app/shared/model/cours.model';
import { getEntities as getCours } from 'app/entities/cours/cours.reducer';
import { IUtilisateur } from 'app/shared/model/utilisateur.model';
import { getEntities as getUtilisateurs } from 'app/entities/utilisateur/utilisateur.reducer';
import { IEvaluation } from 'app/shared/model/evaluation.model';
import { getEntity, updateEntity, createEntity, reset } from './evaluation.reducer';

export const EvaluationUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const cours = useAppSelector(state => state.cours.entities);
  const utilisateurs = useAppSelector(state => state.utilisateur.entities);
  const evaluationEntity = useAppSelector(state => state.evaluation.entity);
  const loading = useAppSelector(state => state.evaluation.loading);
  const updating = useAppSelector(state => state.evaluation.updating);
  const updateSuccess = useAppSelector(state => state.evaluation.updateSuccess);

  const handleClose = () => {
    navigate('/evaluation');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getCours({}));
    dispatch(getUtilisateurs({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.createdAt = convertDateTimeToServer(values.createdAt);

    const entity = {
      ...evaluationEntity,
      ...values,
      cours: cours.find(it => it.id.toString() === values.cours.toString()),
      utilisateur: utilisateurs.find(it => it.id.toString() === values.utilisateur.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          createdAt: displayDefaultDateTime(),
        }
      : {
          ...evaluationEntity,
          createdAt: convertDateTimeFromServer(evaluationEntity.createdAt),
          cours: evaluationEntity?.cours?.id,
          utilisateur: evaluationEntity?.utilisateur?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="marissamayerApp.evaluation.home.createOrEditLabel" data-cy="EvaluationCreateUpdateHeading">
            <Translate contentKey="marissamayerApp.evaluation.home.createOrEditLabel">Create or edit a Evaluation</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="evaluation-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('marissamayerApp.evaluation.commentaire')}
                id="evaluation-commentaire"
                name="commentaire"
                data-cy="commentaire"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('marissamayerApp.evaluation.createdAt')}
                id="evaluation-createdAt"
                name="createdAt"
                data-cy="createdAt"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                id="evaluation-cours"
                name="cours"
                data-cy="cours"
                label={translate('marissamayerApp.evaluation.cours')}
                type="select"
              >
                <option value="" key="0" />
                {cours
                  ? cours.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="evaluation-utilisateur"
                name="utilisateur"
                data-cy="utilisateur"
                label={translate('marissamayerApp.evaluation.utilisateur')}
                type="select"
              >
                <option value="" key="0" />
                {utilisateurs
                  ? utilisateurs.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/evaluation" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default EvaluationUpdate;
