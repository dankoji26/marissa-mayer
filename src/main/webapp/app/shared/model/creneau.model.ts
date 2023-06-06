import { ICours } from 'app/shared/model/cours.model';
import { IUtilisateur } from 'app/shared/model/utilisateur.model';
import { Jour } from 'app/shared/model/enumerations/jour.model';
import { CreneauStatuts } from 'app/shared/model/enumerations/creneau-statuts.model';

export interface ICreneau {
  id?: number;
  jour?: Jour | null;
  heureDebut?: string | null;
  heureFin?: string | null;
  statuts?: CreneauStatuts;
  cours?: ICours;
  user?: IUtilisateur;
}

export const defaultValue: Readonly<ICreneau> = {};
