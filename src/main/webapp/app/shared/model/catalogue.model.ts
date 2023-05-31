import dayjs from 'dayjs';
import { IUtilisateur } from 'app/shared/model/utilisateur.model';
import { ICours } from 'app/shared/model/cours.model';

export interface ICatalogue {
  id?: number;
  nom?: string | null;
  createdAt?: string | null;
  utilisateur?: IUtilisateur | null;
  cours?: ICours[] | null;
}

export const defaultValue: Readonly<ICatalogue> = {};
