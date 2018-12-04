export interface IBenefits {
    id?: number;
    name?: string;
    icon?: string;
    opportunitiesId?: number;
}

export class Benefits implements IBenefits {
    constructor(public id?: number, public name?: string, public icon?: string, public opportunitiesId?: number) {}
}
