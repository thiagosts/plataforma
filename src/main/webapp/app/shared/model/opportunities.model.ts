import { Moment } from 'moment';
import { IPlaces } from 'app/shared/model//places.model';
import { IBenefits } from 'app/shared/model//benefits.model';
import { IRequests } from 'app/shared/model//requests.model';

export const enum OpportunitiesTypeEnums {
    INTERNAL = 'INTERNAL',
    EXTERNAL = 'EXTERNAL',
    CONFIDENCIAL = 'CONFIDENCIAL'
}

export interface IOpportunities {
    id?: number;
    opportunityCode?: string;
    opportunitiesType?: OpportunitiesTypeEnums;
    name?: string;
    status?: string;
    area?: string;
    externalId?: string;
    highlighted?: boolean;
    description?: string;
    startDate?: Moment;
    endDate?: Moment;
    quantity?: number;
    logoDesktopUrl?: string;
    logoMobileUrl?: string;
    hiringType?: string;
    slug?: string;
    places?: IPlaces[];
    benefits?: IBenefits[];
    requests?: IRequests[];
    customersId?: number;
}

export class Opportunities implements IOpportunities {
    constructor(
        public id?: number,
        public opportunityCode?: string,
        public opportunitiesType?: OpportunitiesTypeEnums,
        public name?: string,
        public status?: string,
        public area?: string,
        public externalId?: string,
        public highlighted?: boolean,
        public description?: string,
        public startDate?: Moment,
        public endDate?: Moment,
        public quantity?: number,
        public logoDesktopUrl?: string,
        public logoMobileUrl?: string,
        public hiringType?: string,
        public slug?: string,
        public places?: IPlaces[],
        public benefits?: IBenefits[],
        public requests?: IRequests[],
        public customersId?: number
    ) {
        this.highlighted = this.highlighted || false;
    }
}
