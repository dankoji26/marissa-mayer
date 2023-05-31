import React from 'react';
import { Translate } from 'react-jhipster';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/utilisateur">
        <Translate contentKey="global.menu.entities.utilisateur" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/reservation">
        <Translate contentKey="global.menu.entities.reservation" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/categorie">
        <Translate contentKey="global.menu.entities.categorie" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/cours">
        <Translate contentKey="global.menu.entities.cours" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/paiement">
        <Translate contentKey="global.menu.entities.paiement" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/catalogue">
        <Translate contentKey="global.menu.entities.catalogue" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/notification">
        <Translate contentKey="global.menu.entities.notification" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/evaluation">
        <Translate contentKey="global.menu.entities.evaluation" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/creneau">
        <Translate contentKey="global.menu.entities.creneau" />
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu;
