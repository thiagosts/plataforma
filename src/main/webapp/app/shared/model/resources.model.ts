export interface IResources {
    id?: number;
    name?: string;
    linkUrl?: string;
    iconUrl?: string;
    type?: string;
    title?: string;
    description?: string;
    templatesId?: number;
}

export class Resources implements IResources {
    constructor(
        public id?: number,
        public name?: string,
        public linkUrl?: string,
        public iconUrl?: string,
        public type?: string,
        public title?: string,
        public description?: string,
        public templatesId?: number
    ) {}
}
