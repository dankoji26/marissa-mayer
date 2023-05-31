import dayjs from 'dayjs';
import { IReservation } from 'app/shared/model/reservation.model';

export interface IPaiement {
  id?: number;
  montant?: number;
  createdAt?: string;
  reservation?: IReservation | null;
}

export const defaultValue: Readonly<IPaiement> = {};
