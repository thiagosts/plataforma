import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IResults } from 'app/shared/model/results.model';

type EntityResponseType = HttpResponse<IResults>;
type EntityArrayResponseType = HttpResponse<IResults[]>;

@Injectable({ providedIn: 'root' })
export class ResultsService {
    public resourceUrl = SERVER_API_URL + 'api/results';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/results';

    constructor(private http: HttpClient) {}

    create(results: IResults): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(results);
        return this.http
            .post<IResults>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(results: IResults): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(results);
        return this.http
            .put<IResults>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IResults>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IResults[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IResults[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(results: IResults): IResults {
        const copy: IResults = Object.assign({}, results, {
            startTime: results.startTime != null && results.startTime.isValid() ? results.startTime.toJSON() : null,
            finalTime: results.finalTime != null && results.finalTime.isValid() ? results.finalTime.toJSON() : null,
            maxTime: results.maxTime != null && results.maxTime.isValid() ? results.maxTime.toJSON() : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.startTime = res.body.startTime != null ? moment(res.body.startTime) : null;
            res.body.finalTime = res.body.finalTime != null ? moment(res.body.finalTime) : null;
            res.body.maxTime = res.body.maxTime != null ? moment(res.body.maxTime) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((results: IResults) => {
                results.startTime = results.startTime != null ? moment(results.startTime) : null;
                results.finalTime = results.finalTime != null ? moment(results.finalTime) : null;
                results.maxTime = results.maxTime != null ? moment(results.maxTime) : null;
            });
        }
        return res;
    }
}
