export interface ICustomization {
    id?: number;
    customCSS?: string;
    urlLogo?: string;
}

export class Customization implements ICustomization {
    constructor(public id?: number, public customCSS?: string, public urlLogo?: string) {}
}
