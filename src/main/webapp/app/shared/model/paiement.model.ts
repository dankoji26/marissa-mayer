import dayjs from 'dayjs';
import { IReservation } from 'app/shared/model/reservation.model';

export interface IPaiement {
  id?: number;
  montant?: number | null;
  createdAt?: string | null;
  reservation?: IReservation | null;
}

export const defaultValue: Readonly<IPaiement> = {};
