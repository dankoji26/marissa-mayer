import { IUser } from 'app/shared/model/user.model';
import { ICours } from 'app/shared/model/cours.model';

export interface ICatalogue {
  id?: number;
  nom?: string;
  user?: IUser;
  cours?: ICours[] | null;
}

export const defaultValue: Readonly<ICatalogue> = {};
