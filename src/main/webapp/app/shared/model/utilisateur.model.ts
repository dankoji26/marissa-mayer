import { IUser } from 'app/shared/model/user.model';
import { IReservation } from 'app/shared/model/reservation.model';
import { IEvaluation } from 'app/shared/model/evaluation.model';
import { ICreneau } from 'app/shared/model/creneau.model';

export interface IUtilisateur {
  id?: number;
  instance?: IUser;
  reservations?: IReservation[] | null;
  evaluations?: IEvaluation[] | null;
  creneaus?: ICreneau[] | null;
}

export const defaultValue: Readonly<IUtilisateur> = {};
