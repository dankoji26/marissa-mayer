import dayjs from 'dayjs';

export interface ICategorie {
  id?: number;
  nom?: string;
  createdAt?: string;
}

export const defaultValue: Readonly<ICategorie> = {};
