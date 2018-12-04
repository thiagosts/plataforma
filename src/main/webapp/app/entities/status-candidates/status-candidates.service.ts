import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IStatusCandidates } from 'app/shared/model/status-candidates.model';

type EntityResponseType = HttpResponse<IStatusCandidates>;
type EntityArrayResponseType = HttpResponse<IStatusCandidates[]>;

@Injectable({ providedIn: 'root' })
export class StatusCandidatesService {
    public resourceUrl = SERVER_API_URL + 'api/status-candidates';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/status-candidates';

    constructor(private http: HttpClient) {}

    create(statusCandidates: IStatusCandidates): Observable<EntityResponseType> {
        return this.http.post<IStatusCandidates>(this.resourceUrl, statusCandidates, { observe: 'response' });
    }

    update(statusCandidates: IStatusCandidates): Observable<EntityResponseType> {
        return this.http.put<IStatusCandidates>(this.resourceUrl, statusCandidates, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IStatusCandidates>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IStatusCandidates[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IStatusCandidates[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
