import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IResultsDetails } from 'app/shared/model/results-details.model';

type EntityResponseType = HttpResponse<IResultsDetails>;
type EntityArrayResponseType = HttpResponse<IResultsDetails[]>;

@Injectable({ providedIn: 'root' })
export class ResultsDetailsService {
    public resourceUrl = SERVER_API_URL + 'api/results-details';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/results-details';

    constructor(private http: HttpClient) {}

    create(resultsDetails: IResultsDetails): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(resultsDetails);
        return this.http
            .post<IResultsDetails>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(resultsDetails: IResultsDetails): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(resultsDetails);
        return this.http
            .put<IResultsDetails>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IResultsDetails>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IResultsDetails[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IResultsDetails[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(resultsDetails: IResultsDetails): IResultsDetails {
        const copy: IResultsDetails = Object.assign({}, resultsDetails, {
            createdDate:
                resultsDetails.createdDate != null && resultsDetails.createdDate.isValid() ? resultsDetails.createdDate.toJSON() : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.createdDate = res.body.createdDate != null ? moment(res.body.createdDate) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((resultsDetails: IResultsDetails) => {
                resultsDetails.createdDate = resultsDetails.createdDate != null ? moment(resultsDetails.createdDate) : null;
            });
        }
        return res;
    }
}
