export interface IRequests {
    id?: number;
    name?: string;
    opportunitiesId?: number;
}

export class Requests implements IRequests {
    constructor(public id?: number, public name?: string, public opportunitiesId?: number) {}
}
