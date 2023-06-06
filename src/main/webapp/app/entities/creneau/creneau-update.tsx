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
import { ICreneau } from 'app/shared/model/creneau.model';
import { Jour } from 'app/shared/model/enumerations/jour.model';
import { CreneauStatuts } from 'app/shared/model/enumerations/creneau-statuts.model';
import { getEntity, updateEntity, createEntity, reset } from './creneau.reducer';

export const CreneauUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const cours = useAppSelector(state => state.cours.entities);
  const utilisateurs = useAppSelector(state => state.utilisateur.entities);
  const creneauEntity = useAppSelector(state => state.creneau.entity);
  const loading = useAppSelector(state => state.creneau.loading);
  const updating = useAppSelector(state => state.creneau.updating);
  const updateSuccess = useAppSelector(state => state.creneau.updateSuccess);
  const jourValues = Object.keys(Jour);
  const creneauStatutsValues = Object.keys(CreneauStatuts);

  const handleClose = () => {
    navigate('/creneau');
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
    const entity = {
      ...creneauEntity,
      ...values,
      cours: cours.find(it => it.id.toString() === values.cours.toString()),
      user: utilisateurs.find(it => it.id.toString() === values.user.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          jour: 'LUNDI',
          statuts: 'LIBRE',
          ...creneauEntity,
          cours: creneauEntity?.cours?.id,
          user: creneauEntity?.user?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="marissamayerApp.creneau.home.createOrEditLabel" data-cy="CreneauCreateUpdateHeading">
            <Translate contentKey="marissamayerApp.creneau.home.createOrEditLabel">Create or edit a Creneau</Translate>
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
                  id="creneau-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField label={translate('marissamayerApp.creneau.jour')} id="creneau-jour" name="jour" data-cy="jour" type="select">
                {jourValues.map(jour => (
                  <option value={jour} key={jour}>
                    {translate('marissamayerApp.Jour.' + jour)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('marissamayerApp.creneau.heureDebut')}
                id="creneau-heureDebut"
                name="heureDebut"
                data-cy="heureDebut"
                type="text"
                validate={{
                  pattern: {
                    value: /^([0-2]\d|1[0-9]|2[0-3]):[0-5]\d$/,
                    message: translate('entity.validation.pattern', { pattern: '^([0-2]\\d|1[0-9]|2[0-3]):[0-5]\\d$' }),
                  },
                }}
              />
              <ValidatedField
                label={translate('marissamayerApp.creneau.heureFin')}
                id="creneau-heureFin"
                name="heureFin"
                data-cy="heureFin"
                type="text"
                validate={{
                  pattern: {
                    value: /^([0-2]\d|1[0-9]|2[0-3]):[0-5]\d$/,
                    message: translate('entity.validation.pattern', { pattern: '^([0-2]\\d|1[0-9]|2[0-3]):[0-5]\\d$' }),
                  },
                }}
              />
              <ValidatedField
                label={translate('marissamayerApp.creneau.statuts')}
                id="creneau-statuts"
                name="statuts"
                data-cy="statuts"
                type="select"
              >
                {creneauStatutsValues.map(creneauStatuts => (
                  <option value={creneauStatuts} key={creneauStatuts}>
                    {translate('marissamayerApp.CreneauStatuts.' + creneauStatuts)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                id="creneau-cours"
                name="cours"
                data-cy="cours"
                label={translate('marissamayerApp.creneau.cours')}
                type="select"
                required
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
              <FormText>
                <Translate contentKey="entity.validation.required">This field is required.</Translate>
              </FormText>
              <ValidatedField
                id="creneau-user"
                name="user"
                data-cy="user"
                label={translate('marissamayerApp.creneau.user')}
                type="select"
                required
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
              <FormText>
                <Translate contentKey="entity.validation.required">This field is required.</Translate>
              </FormText>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/creneau" replace color="info">
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

export default CreneauUpdate;
