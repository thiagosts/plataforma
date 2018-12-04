import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IAnswers } from 'app/shared/model/answers.model';
import { AnswersService } from './answers.service';
import { IResultsDetails } from 'app/shared/model/results-details.model';
import { ResultsDetailsService } from 'app/entities/results-details';
import { IQuestions } from 'app/shared/model/questions.model';
import { QuestionsService } from 'app/entities/questions';

@Component({
    selector: 'jhi-answers-update',
    templateUrl: './answers-update.component.html'
})
export class AnswersUpdateComponent implements OnInit {
    answers: IAnswers;
    isSaving: boolean;

    resultsdetails: IResultsDetails[];

    questions: IQuestions[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private answersService: AnswersService,
        private resultsDetailsService: ResultsDetailsService,
        private questionsService: QuestionsService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ answers }) => {
            this.answers = answers;
        });
        this.resultsDetailsService.query().subscribe(
            (res: HttpResponse<IResultsDetails[]>) => {
                this.resultsdetails = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.questionsService.query().subscribe(
            (res: HttpResponse<IQuestions[]>) => {
                this.questions = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.answers.id !== undefined) {
            this.subscribeToSaveResponse(this.answersService.update(this.answers));
        } else {
            this.subscribeToSaveResponse(this.answersService.create(this.answers));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IAnswers>>) {
        result.subscribe((res: HttpResponse<IAnswers>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackResultsDetailsById(index: number, item: IResultsDetails) {
        return item.id;
    }

    trackQuestionsById(index: number, item: IQuestions) {
        return item.id;
    }
}
