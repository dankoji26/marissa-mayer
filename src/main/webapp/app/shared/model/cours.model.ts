import { IEvaluation } from 'app/shared/model/evaluation.model';
import { ICreneau } from 'app/shared/model/creneau.model';
import { IReservation } from 'app/shared/model/reservation.model';
import { ICategorie } from 'app/shared/model/categorie.model';
import { ICatalogue } from 'app/shared/model/catalogue.model';

export interface ICours {
  id?: number;
  nom?: string;
  description?: string;
  duree?: number | null;
  prerequis?: string | null;
  prix?: number | null;
  evaluations?: IEvaluation[] | null;
  creneaus?: ICreneau[] | null;
  reservations?: IReservation[] | null;
  categories?: ICategorie[] | null;
  catalogue?: ICatalogue;
}

export const defaultValue: Readonly<ICours> = {};
