export interface IPreferences {
    id?: number;
    area?: string;
    candidatesId?: number;
}

export class Preferences implements IPreferences {
    constructor(public id?: number, public area?: string, public candidatesId?: number) {}
}
