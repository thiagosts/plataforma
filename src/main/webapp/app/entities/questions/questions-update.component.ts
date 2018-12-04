import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IQuestions } from 'app/shared/model/questions.model';
import { QuestionsService } from './questions.service';
import { IMatchings } from 'app/shared/model/matchings.model';
import { MatchingsService } from 'app/entities/matchings';

@Component({
    selector: 'jhi-questions-update',
    templateUrl: './questions-update.component.html'
})
export class QuestionsUpdateComponent implements OnInit {
    questions: IQuestions;
    isSaving: boolean;

    matchings: IMatchings[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private questionsService: QuestionsService,
        private matchingsService: MatchingsService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ questions }) => {
            this.questions = questions;
        });
        this.matchingsService.query().subscribe(
            (res: HttpResponse<IMatchings[]>) => {
                this.matchings = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.questions.id !== undefined) {
            this.subscribeToSaveResponse(this.questionsService.update(this.questions));
        } else {
            this.subscribeToSaveResponse(this.questionsService.create(this.questions));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IQuestions>>) {
        result.subscribe((res: HttpResponse<IQuestions>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackMatchingsById(index: number, item: IMatchings) {
        return item.id;
    }
}
