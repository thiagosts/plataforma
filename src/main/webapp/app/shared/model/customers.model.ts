import { IPortal } from 'app/shared/model//portal.model';
import { IOpportunities } from 'app/shared/model//opportunities.model';

export interface ICustomers {
    id?: number;
    name?: string;
    documentCode?: string;
    companySize?: string;
    description?: string;
    customersCode?: string;
    logoDesktopUrl?: string;
    logoMobileUrl?: string;
    active?: boolean;
    portals?: IPortal[];
    opportunities?: IOpportunities[];
}

export class Customers implements ICustomers {
    constructor(
        public id?: number,
        public name?: string,
        public documentCode?: string,
        public companySize?: string,
        public description?: string,
        public customersCode?: string,
        public logoDesktopUrl?: string,
        public logoMobileUrl?: string,
        public active?: boolean,
        public portals?: IPortal[],
        public opportunities?: IOpportunities[]
    ) {
        this.active = this.active || false;
    }
}
