import dayjs from 'dayjs';
import { IUtilisateur } from 'app/shared/model/utilisateur.model';
import { ReservationStatuts } from 'app/shared/model/enumerations/reservation-statuts.model';

export interface IReservation {
  id?: number;
  statuts?: ReservationStatuts;
  date?: string;
  createdAt?: string;
  utilisateur?: IUtilisateur | null;
}

export const defaultValue: Readonly<IReservation> = {};
