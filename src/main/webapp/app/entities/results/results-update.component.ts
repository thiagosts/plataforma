import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { IResults } from 'app/shared/model/results.model';
import { ResultsService } from './results.service';
import { IStatusCandidates } from 'app/shared/model/status-candidates.model';
import { StatusCandidatesService } from 'app/entities/status-candidates';
import { IMatchings } from 'app/shared/model/matchings.model';
import { MatchingsService } from 'app/entities/matchings';

@Component({
    selector: 'jhi-results-update',
    templateUrl: './results-update.component.html'
})
export class ResultsUpdateComponent implements OnInit {
    results: IResults;
    isSaving: boolean;

    statuscandidates: IStatusCandidates[];

    matchings: IMatchings[];
    startTime: string;
    finalTime: string;
    maxTime: string;

    constructor(
        private jhiAlertService: JhiAlertService,
        private resultsService: ResultsService,
        private statusCandidatesService: StatusCandidatesService,
        private matchingsService: MatchingsService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ results }) => {
            this.results = results;
            this.startTime = this.results.startTime != null ? this.results.startTime.format(DATE_TIME_FORMAT) : null;
            this.finalTime = this.results.finalTime != null ? this.results.finalTime.format(DATE_TIME_FORMAT) : null;
            this.maxTime = this.results.maxTime != null ? this.results.maxTime.format(DATE_TIME_FORMAT) : null;
        });
        this.statusCandidatesService.query().subscribe(
            (res: HttpResponse<IStatusCandidates[]>) => {
                this.statuscandidates = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
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
        this.results.startTime = this.startTime != null ? moment(this.startTime, DATE_TIME_FORMAT) : null;
        this.results.finalTime = this.finalTime != null ? moment(this.finalTime, DATE_TIME_FORMAT) : null;
        this.results.maxTime = this.maxTime != null ? moment(this.maxTime, DATE_TIME_FORMAT) : null;
        if (this.results.id !== undefined) {
            this.subscribeToSaveResponse(this.resultsService.update(this.results));
        } else {
            this.subscribeToSaveResponse(this.resultsService.create(this.results));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IResults>>) {
        result.subscribe((res: HttpResponse<IResults>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackStatusCandidatesById(index: number, item: IStatusCandidates) {
        return item.id;
    }

    trackMatchingsById(index: number, item: IMatchings) {
        return item.id;
    }
}
