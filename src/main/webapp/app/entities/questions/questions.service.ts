import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IQuestions } from 'app/shared/model/questions.model';

type EntityResponseType = HttpResponse<IQuestions>;
type EntityArrayResponseType = HttpResponse<IQuestions[]>;

@Injectable({ providedIn: 'root' })
export class QuestionsService {
    public resourceUrl = SERVER_API_URL + 'api/questions';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/questions';

    constructor(private http: HttpClient) {}

    create(questions: IQuestions): Observable<EntityResponseType> {
        return this.http.post<IQuestions>(this.resourceUrl, questions, { observe: 'response' });
    }

    update(questions: IQuestions): Observable<EntityResponseType> {
        return this.http.put<IQuestions>(this.resourceUrl, questions, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IQuestions>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IQuestions[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IQuestions[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
