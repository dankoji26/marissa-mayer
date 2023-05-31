import dayjs from 'dayjs';
import { ICours } from 'app/shared/model/cours.model';
import { IUtilisateur } from 'app/shared/model/utilisateur.model';

export interface IEvaluation {
  id?: number;
  commentaire?: string | null;
  createdAt?: string | null;
  cours?: ICours | null;
  utilisateur?: IUtilisateur | null;
}

export const defaultValue: Readonly<IEvaluation> = {};
