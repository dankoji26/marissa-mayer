import dayjs from 'dayjs';
import { IEvaluation } from 'app/shared/model/evaluation.model';
import { ICreneau } from 'app/shared/model/creneau.model';
import { ICatalogue } from 'app/shared/model/catalogue.model';

export interface ICours {
  id?: number;
  nom?: string;
  description?: string;
  duree?: string | null;
  prerequis?: string | null;
  createdAt?: string;
  evaluations?: IEvaluation[] | null;
  creneaus?: ICreneau[] | null;
  catalogue?: ICatalogue | null;
}

export const defaultValue: Readonly<ICours> = {};
