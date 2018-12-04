import { Moment } from 'moment';
import { IAnswers } from 'app/shared/model//answers.model';

export interface IResultsDetails {
    id?: number;
    value?: number;
    result?: string;
    createdDate?: Moment;
    answers?: IAnswers[];
    resultsId?: number;
}

export class ResultsDetails implements IResultsDetails {
    constructor(
        public id?: number,
        public value?: number,
        public result?: string,
        public createdDate?: Moment,
        public answers?: IAnswers[],
        public resultsId?: number
    ) {}
}
