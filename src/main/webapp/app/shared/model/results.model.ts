import { Moment } from 'moment';
import { IResultsDetails } from 'app/shared/model//results-details.model';

export interface IResults {
    id?: number;
    value?: number;
    startTime?: Moment;
    finalTime?: Moment;
    maxTime?: Moment;
    resultsdetails?: IResultsDetails[];
    statuscandidatesId?: number;
    matchingsId?: number;
}

export class Results implements IResults {
    constructor(
        public id?: number,
        public value?: number,
        public startTime?: Moment,
        public finalTime?: Moment,
        public maxTime?: Moment,
        public resultsdetails?: IResultsDetails[],
        public statuscandidatesId?: number,
        public matchingsId?: number
    ) {}
}
