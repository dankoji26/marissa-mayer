import dayjs from 'dayjs';
import { IUtilisateur } from 'app/shared/model/utilisateur.model';
import { NotificationStatus } from 'app/shared/model/enumerations/notification-status.model';

export interface INotification {
  id?: number;
  message?: string | null;
  statuts?: NotificationStatus | null;
  createdAt?: string | null;
  utilisateur?: IUtilisateur | null;
}

export const defaultValue: Readonly<INotification> = {};
