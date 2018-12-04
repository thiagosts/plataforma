import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IMatchings } from 'app/shared/model/matchings.model';

type EntityResponseType = HttpResponse<IMatchings>;
type EntityArrayResponseType = HttpResponse<IMatchings[]>;

@Injectable({ providedIn: 'root' })
export class MatchingsService {
    public resourceUrl = SERVER_API_URL + 'api/matchings';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/matchings';

    constructor(private http: HttpClient) {}

    create(matchings: IMatchings): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(matchings);
        return this.http
            .post<IMatchings>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(matchings: IMatchings): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(matchings);
        return this.http
            .put<IMatchings>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IMatchings>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IMatchings[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IMatchings[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(matchings: IMatchings): IMatchings {
        const copy: IMatchings = Object.assign({}, matchings, {
            createdDate: matchings.createdDate != null && matchings.createdDate.isValid() ? matchings.createdDate.toJSON() : null,
            lastModifiedDate:
                matchings.lastModifiedDate != null && matchings.lastModifiedDate.isValid() ? matchings.lastModifiedDate.toJSON() : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.createdDate = res.body.createdDate != null ? moment(res.body.createdDate) : null;
            res.body.lastModifiedDate = res.body.lastModifiedDate != null ? moment(res.body.lastModifiedDate) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((matchings: IMatchings) => {
                matchings.createdDate = matchings.createdDate != null ? moment(matchings.createdDate) : null;
                matchings.lastModifiedDate = matchings.lastModifiedDate != null ? moment(matchings.lastModifiedDate) : null;
            });
        }
        return res;
    }
}
