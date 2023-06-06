import dayjs from 'dayjs';
import { IUtilisateur } from 'app/shared/model/utilisateur.model';
import { ICours } from 'app/shared/model/cours.model';
import { ReservationStatuts } from 'app/shared/model/enumerations/reservation-statuts.model';

export interface IReservation {
  id?: number;
  statuts?: ReservationStatuts;
  date?: string;
  user?: IUtilisateur;
  cours?: ICours;
}

export const defaultValue: Readonly<IReservation> = {};
