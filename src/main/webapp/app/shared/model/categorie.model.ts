import dayjs from 'dayjs';

export interface ICategorie {
  id?: number;
  nom?: string | null;
  createdAt?: string | null;
}

export const defaultValue: Readonly<ICategorie> = {};
