import { ICours } from 'app/shared/model/cours.model';

export interface ICategorie {
  id?: number;
  nom?: string;
  cours?: ICours[];
}

export const defaultValue: Readonly<ICategorie> = {};
