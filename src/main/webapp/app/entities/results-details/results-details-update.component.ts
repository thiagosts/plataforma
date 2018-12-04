import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { IResultsDetails } from 'app/shared/model/results-details.model';
import { ResultsDetailsService } from './results-details.service';
import { IResults } from 'app/shared/model/results.model';
import { ResultsService } from 'app/entities/results';

@Component({
    selector: 'jhi-results-details-update',
    templateUrl: './results-details-update.component.html'
})
export class ResultsDetailsUpdateComponent implements OnInit {
    resultsDetails: IResultsDetails;
    isSaving: boolean;

    results: IResults[];
    createdDate: string;

    constructor(
        private jhiAlertService: JhiAlertService,
        private resultsDetailsService: ResultsDetailsService,
        private resultsService: ResultsService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ resultsDetails }) => {
            this.resultsDetails = resultsDetails;
            this.createdDate = this.resultsDetails.createdDate != null ? this.resultsDetails.createdDate.format(DATE_TIME_FORMAT) : null;
        });
        this.resultsService.query().subscribe(
            (res: HttpResponse<IResults[]>) => {
                this.results = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.resultsDetails.createdDate = this.createdDate != null ? moment(this.createdDate, DATE_TIME_FORMAT) : null;
        if (this.resultsDetails.id !== undefined) {
            this.subscribeToSaveResponse(this.resultsDetailsService.update(this.resultsDetails));
        } else {
            this.subscribeToSaveResponse(this.resultsDetailsService.create(this.resultsDetails));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IResultsDetails>>) {
        result.subscribe((res: HttpResponse<IResultsDetails>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackResultsById(index: number, item: IResults) {
        return item.id;
    }
}
