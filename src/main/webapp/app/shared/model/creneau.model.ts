import { ICours } from 'app/shared/model/cours.model';
import { IUtilisateur } from 'app/shared/model/utilisateur.model';
import { Jour } from 'app/shared/model/enumerations/jour.model';
import { CreneauStatuts } from 'app/shared/model/enumerations/creneau-statuts.model';

export interface ICreneau {
  id?: number;
  jour?: Jour | null;
  heureDebut?: string | null;
  heureFin?: string | null;
  statuts?: CreneauStatuts | null;
  cours?: ICours | null;
  utilisateur?: IUtilisateur | null;
}

export const defaultValue: Readonly<ICreneau> = {};
