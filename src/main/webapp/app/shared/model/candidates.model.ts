import { Moment } from 'moment';
import { IStatusCandidates } from 'app/shared/model//status-candidates.model';
import { IPreferences } from 'app/shared/model//preferences.model';

export interface ICandidates {
    id?: number;
    name?: string;
    email?: string;
    celPhone?: string;
    area?: string;
    dataOfBirth?: Moment;
    occupation?: string;
    pictureUrl?: string;
    salesForceId?: string;
    placesId?: number;
    statuscandidates?: IStatusCandidates[];
    preferences?: IPreferences[];
}

export class Candidates implements ICandidates {
    constructor(
        public id?: number,
        public name?: string,
        public email?: string,
        public celPhone?: string,
        public area?: string,
        public dataOfBirth?: Moment,
        public occupation?: string,
        public pictureUrl?: string,
        public salesForceId?: string,
        public placesId?: number,
        public statuscandidates?: IStatusCandidates[],
        public preferences?: IPreferences[]
    ) {}
}
