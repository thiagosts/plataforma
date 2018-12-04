import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITemplates } from 'app/shared/model/templates.model';

type EntityResponseType = HttpResponse<ITemplates>;
type EntityArrayResponseType = HttpResponse<ITemplates[]>;

@Injectable({ providedIn: 'root' })
export class TemplatesService {
    public resourceUrl = SERVER_API_URL + 'api/templates';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/templates';

    constructor(private http: HttpClient) {}

    create(templates: ITemplates): Observable<EntityResponseType> {
        return this.http.post<ITemplates>(this.resourceUrl, templates, { observe: 'response' });
    }

    update(templates: ITemplates): Observable<EntityResponseType> {
        return this.http.put<ITemplates>(this.resourceUrl, templates, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ITemplates>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ITemplates[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ITemplates[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
