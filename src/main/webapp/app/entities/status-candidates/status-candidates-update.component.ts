import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IStatusCandidates } from 'app/shared/model/status-candidates.model';
import { StatusCandidatesService } from './status-candidates.service';
import { ICandidates } from 'app/shared/model/candidates.model';
import { CandidatesService } from 'app/entities/candidates';

@Component({
    selector: 'jhi-status-candidates-update',
    templateUrl: './status-candidates-update.component.html'
})
export class StatusCandidatesUpdateComponent implements OnInit {
    statusCandidates: IStatusCandidates;
    isSaving: boolean;

    candidates: ICandidates[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private statusCandidatesService: StatusCandidatesService,
        private candidatesService: CandidatesService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ statusCandidates }) => {
            this.statusCandidates = statusCandidates;
        });
        this.candidatesService.query().subscribe(
            (res: HttpResponse<ICandidates[]>) => {
                this.candidates = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.statusCandidates.id !== undefined) {
            this.subscribeToSaveResponse(this.statusCandidatesService.update(this.statusCandidates));
        } else {
            this.subscribeToSaveResponse(this.statusCandidatesService.create(this.statusCandidates));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IStatusCandidates>>) {
        result.subscribe((res: HttpResponse<IStatusCandidates>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackCandidatesById(index: number, item: ICandidates) {
        return item.id;
    }
}
