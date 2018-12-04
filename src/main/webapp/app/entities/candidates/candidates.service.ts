import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICandidates } from 'app/shared/model/candidates.model';

type EntityResponseType = HttpResponse<ICandidates>;
type EntityArrayResponseType = HttpResponse<ICandidates[]>;

@Injectable({ providedIn: 'root' })
export class CandidatesService {
    public resourceUrl = SERVER_API_URL + 'api/candidates';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/candidates';

    constructor(private http: HttpClient) {}

    create(candidates: ICandidates): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(candidates);
        return this.http
            .post<ICandidates>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(candidates: ICandidates): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(candidates);
        return this.http
            .put<ICandidates>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<ICandidates>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ICandidates[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ICandidates[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(candidates: ICandidates): ICandidates {
        const copy: ICandidates = Object.assign({}, candidates, {
            dataOfBirth: candidates.dataOfBirth != null && candidates.dataOfBirth.isValid() ? candidates.dataOfBirth.toJSON() : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.dataOfBirth = res.body.dataOfBirth != null ? moment(res.body.dataOfBirth) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((candidates: ICandidates) => {
                candidates.dataOfBirth = candidates.dataOfBirth != null ? moment(candidates.dataOfBirth) : null;
            });
        }
        return res;
    }
}
