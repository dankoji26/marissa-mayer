import { IReservation } from 'app/shared/model/reservation.model';

export interface IPaiement {
  id?: number;
  montant?: number;
  reservation?: IReservation;
}

export const defaultValue: Readonly<IPaiement> = {};
