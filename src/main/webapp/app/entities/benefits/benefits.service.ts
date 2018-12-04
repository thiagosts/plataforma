import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IBenefits } from 'app/shared/model/benefits.model';

type EntityResponseType = HttpResponse<IBenefits>;
type EntityArrayResponseType = HttpResponse<IBenefits[]>;

@Injectable({ providedIn: 'root' })
export class BenefitsService {
    public resourceUrl = SERVER_API_URL + 'api/benefits';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/benefits';

    constructor(private http: HttpClient) {}

    create(benefits: IBenefits): Observable<EntityResponseType> {
        return this.http.post<IBenefits>(this.resourceUrl, benefits, { observe: 'response' });
    }

    update(benefits: IBenefits): Observable<EntityResponseType> {
        return this.http.put<IBenefits>(this.resourceUrl, benefits, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IBenefits>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IBenefits[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IBenefits[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
