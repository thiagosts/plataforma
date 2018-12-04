import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPortal } from 'app/shared/model/portal.model';

type EntityResponseType = HttpResponse<IPortal>;
type EntityArrayResponseType = HttpResponse<IPortal[]>;

@Injectable({ providedIn: 'root' })
export class PortalService {
    public resourceUrl = SERVER_API_URL + 'api/portals';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/portals';

    constructor(private http: HttpClient) {}

    create(portal: IPortal): Observable<EntityResponseType> {
        return this.http.post<IPortal>(this.resourceUrl, portal, { observe: 'response' });
    }

    update(portal: IPortal): Observable<EntityResponseType> {
        return this.http.put<IPortal>(this.resourceUrl, portal, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IPortal>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IPortal[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IPortal[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
