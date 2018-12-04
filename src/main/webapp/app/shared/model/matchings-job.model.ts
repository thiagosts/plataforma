import { IMatchings } from 'app/shared/model//matchings.model';

export interface IMatchingsJob {
    id?: number;
    cutNote?: number;
    order?: number;
    require?: string;
    opportunitiesId?: number;
    matchings?: IMatchings[];
}

export class MatchingsJob implements IMatchingsJob {
    constructor(
        public id?: number,
        public cutNote?: number,
        public order?: number,
        public require?: string,
        public opportunitiesId?: number,
        public matchings?: IMatchings[]
    ) {}
}
