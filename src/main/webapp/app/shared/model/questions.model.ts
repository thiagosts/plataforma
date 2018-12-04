import { IAnswers } from 'app/shared/model//answers.model';

export interface IQuestions {
    id?: number;
    name?: string;
    description?: string;
    type?: string;
    order?: number;
    require?: string;
    answers?: IAnswers[];
    matchingsId?: number;
}

export class Questions implements IQuestions {
    constructor(
        public id?: number,
        public name?: string,
        public description?: string,
        public type?: string,
        public order?: number,
        public require?: string,
        public answers?: IAnswers[],
        public matchingsId?: number
    ) {}
}
