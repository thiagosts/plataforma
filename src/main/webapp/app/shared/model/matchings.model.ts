import { Moment } from 'moment';
import { IQuestions } from 'app/shared/model//questions.model';
import { IResults } from 'app/shared/model//results.model';

export interface IMatchings {
    id?: number;
    name?: string;
    type?: string;
    createdDate?: Moment;
    lastModifiedDate?: Moment;
    time?: number;
    isDefault?: string;
    customizationId?: number;
    questions?: IQuestions[];
    results?: IResults[];
    matchingsjobId?: number;
}

export class Matchings implements IMatchings {
    constructor(
        public id?: number,
        public name?: string,
        public type?: string,
        public createdDate?: Moment,
        public lastModifiedDate?: Moment,
        public time?: number,
        public isDefault?: string,
        public customizationId?: number,
        public questions?: IQuestions[],
        public results?: IResults[],
        public matchingsjobId?: number
    ) {}
}
