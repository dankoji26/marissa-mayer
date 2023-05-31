import dayjs from 'dayjs';
import { IReservation } from 'app/shared/model/reservation.model';
import { IEvaluation } from 'app/shared/model/evaluation.model';
import { INotification } from 'app/shared/model/notification.model';
import { ICreneau } from 'app/shared/model/creneau.model';
import { Role } from 'app/shared/model/enumerations/role.model';

export interface IUtilisateur {
  id?: number;
  nom?: string;
  prenom?: string;
  email?: string;
  password?: string;
  createdAt?: string;
  role?: Role;
  reservations?: IReservation[] | null;
  evaluations?: IEvaluation[] | null;
  notifications?: INotification[] | null;
  creneaus?: ICreneau[] | null;
}

export const defaultValue: Readonly<IUtilisateur> = {};
