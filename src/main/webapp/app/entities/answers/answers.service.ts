import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAnswers } from 'app/shared/model/answers.model';

type EntityResponseType = HttpResponse<IAnswers>;
type EntityArrayResponseType = HttpResponse<IAnswers[]>;

@Injectable({ providedIn: 'root' })
export class AnswersService {
    public resourceUrl = SERVER_API_URL + 'api/answers';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/answers';

    constructor(private http: HttpClient) {}

    create(answers: IAnswers): Observable<EntityResponseType> {
        return this.http.post<IAnswers>(this.resourceUrl, answers, { observe: 'response' });
    }

    update(answers: IAnswers): Observable<EntityResponseType> {
        return this.http.put<IAnswers>(this.resourceUrl, answers, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IAnswers>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IAnswers[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IAnswers[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
