export interface IAnswers {
    id?: number;
    name?: string;
    value?: number;
    maxSize?: number;
    resultsDetailsId?: number;
    questionsId?: number;
}

export class Answers implements IAnswers {
    constructor(
        public id?: number,
        public name?: string,
        public value?: number,
        public maxSize?: number,
        public resultsDetailsId?: number,
        public questionsId?: number
    ) {}
}
