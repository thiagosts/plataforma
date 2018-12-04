import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICustomization } from 'app/shared/model/customization.model';

type EntityResponseType = HttpResponse<ICustomization>;
type EntityArrayResponseType = HttpResponse<ICustomization[]>;

@Injectable({ providedIn: 'root' })
export class CustomizationService {
    public resourceUrl = SERVER_API_URL + 'api/customizations';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/customizations';

    constructor(private http: HttpClient) {}

    create(customization: ICustomization): Observable<EntityResponseType> {
        return this.http.post<ICustomization>(this.resourceUrl, customization, { observe: 'response' });
    }

    update(customization: ICustomization): Observable<EntityResponseType> {
        return this.http.put<ICustomization>(this.resourceUrl, customization, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ICustomization>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ICustomization[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ICustomization[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
