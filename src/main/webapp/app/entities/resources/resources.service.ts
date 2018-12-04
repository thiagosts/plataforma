import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IResources } from 'app/shared/model/resources.model';

type EntityResponseType = HttpResponse<IResources>;
type EntityArrayResponseType = HttpResponse<IResources[]>;

@Injectable({ providedIn: 'root' })
export class ResourcesService {
    public resourceUrl = SERVER_API_URL + 'api/resources';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/resources';

    constructor(private http: HttpClient) {}

    create(resources: IResources): Observable<EntityResponseType> {
        return this.http.post<IResources>(this.resourceUrl, resources, { observe: 'response' });
    }

    update(resources: IResources): Observable<EntityResponseType> {
        return this.http.put<IResources>(this.resourceUrl, resources, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IResources>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IResources[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IResources[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
