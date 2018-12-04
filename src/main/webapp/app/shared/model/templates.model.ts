export interface ITemplates {
    id?: number;
    name?: string;
    customCss?: string;
}

export class Templates implements ITemplates {
    constructor(public id?: number, public name?: string, public customCss?: string) {}
}
