export interface IPortal {
    id?: number;
    name?: string;
    type?: string;
    templatesId?: number;
    customersId?: number;
}

export class Portal implements IPortal {
    constructor(public id?: number, public name?: string, public type?: string, public templatesId?: number, public customersId?: number) {}
}
