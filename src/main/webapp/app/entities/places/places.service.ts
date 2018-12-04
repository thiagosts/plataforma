import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPlaces } from 'app/shared/model/places.model';

type EntityResponseType = HttpResponse<IPlaces>;
type EntityArrayResponseType = HttpResponse<IPlaces[]>;

@Injectable({ providedIn: 'root' })
export class PlacesService {
    public resourceUrl = SERVER_API_URL + 'api/places';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/places';

    constructor(private http: HttpClient) {}

    create(places: IPlaces): Observable<EntityResponseType> {
        return this.http.post<IPlaces>(this.resourceUrl, places, { observe: 'response' });
    }

    update(places: IPlaces): Observable<EntityResponseType> {
        return this.http.put<IPlaces>(this.resourceUrl, places, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IPlaces>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IPlaces[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IPlaces[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
