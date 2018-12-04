import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IMatchingsJob } from 'app/shared/model/matchings-job.model';

type EntityResponseType = HttpResponse<IMatchingsJob>;
type EntityArrayResponseType = HttpResponse<IMatchingsJob[]>;

@Injectable({ providedIn: 'root' })
export class MatchingsJobService {
    public resourceUrl = SERVER_API_URL + 'api/matchings-jobs';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/matchings-jobs';

    constructor(private http: HttpClient) {}

    create(matchingsJob: IMatchingsJob): Observable<EntityResponseType> {
        return this.http.post<IMatchingsJob>(this.resourceUrl, matchingsJob, { observe: 'response' });
    }

    update(matchingsJob: IMatchingsJob): Observable<EntityResponseType> {
        return this.http.put<IMatchingsJob>(this.resourceUrl, matchingsJob, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IMatchingsJob>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IMatchingsJob[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IMatchingsJob[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
