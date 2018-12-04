import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IRequests } from 'app/shared/model/requests.model';

type EntityResponseType = HttpResponse<IRequests>;
type EntityArrayResponseType = HttpResponse<IRequests[]>;

@Injectable({ providedIn: 'root' })
export class RequestsService {
    public resourceUrl = SERVER_API_URL + 'api/requests';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/requests';

    constructor(private http: HttpClient) {}

    create(requests: IRequests): Observable<EntityResponseType> {
        return this.http.post<IRequests>(this.resourceUrl, requests, { observe: 'response' });
    }

    update(requests: IRequests): Observable<EntityResponseType> {
        return this.http.put<IRequests>(this.resourceUrl, requests, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IRequests>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IRequests[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IRequests[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
