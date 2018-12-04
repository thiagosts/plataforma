import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IRequests } from 'app/shared/model/requests.model';
import { RequestsService } from './requests.service';
import { IOpportunities } from 'app/shared/model/opportunities.model';
import { OpportunitiesService } from 'app/entities/opportunities';

@Component({
    selector: 'jhi-requests-update',
    templateUrl: './requests-update.component.html'
})
export class RequestsUpdateComponent implements OnInit {
    requests: IRequests;
    isSaving: boolean;

    opportunities: IOpportunities[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private requestsService: RequestsService,
        private opportunitiesService: OpportunitiesService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ requests }) => {
            this.requests = requests;
        });
        this.opportunitiesService.query().subscribe(
            (res: HttpResponse<IOpportunities[]>) => {
                this.opportunities = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.requests.id !== undefined) {
            this.subscribeToSaveResponse(this.requestsService.update(this.requests));
        } else {
            this.subscribeToSaveResponse(this.requestsService.create(this.requests));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IRequests>>) {
        result.subscribe((res: HttpResponse<IRequests>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackOpportunitiesById(index: number, item: IOpportunities) {
        return item.id;
    }
}
