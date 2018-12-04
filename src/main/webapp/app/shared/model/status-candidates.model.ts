import { IResults } from 'app/shared/model//results.model';

export interface IStatusCandidates {
    id?: number;
    stageName?: string;
    source?: string;
    subStageName?: string;
    candidatesId?: number;
    results?: IResults[];
}

export class StatusCandidates implements IStatusCandidates {
    constructor(
        public id?: number,
        public stageName?: string,
        public source?: string,
        public subStageName?: string,
        public candidatesId?: number,
        public results?: IResults[]
    ) {}
}
