export interface IPlaces {
    id?: number;
    address?: string;
    district?: string;
    city?: string;
    zone?: string;
    stateProvince?: string;
    country?: string;
    zipCode?: string;
    opportunitiesId?: number;
}

export class Places implements IPlaces {
    constructor(
        public id?: number,
        public address?: string,
        public district?: string,
        public city?: string,
        public zone?: string,
        public stateProvince?: string,
        public country?: string,
        public zipCode?: string,
        public opportunitiesId?: number
    ) {}
}
