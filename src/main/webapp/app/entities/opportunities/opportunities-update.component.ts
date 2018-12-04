import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { IOpportunities } from 'app/shared/model/opportunities.model';
import { OpportunitiesService } from './opportunities.service';
import { ICustomers } from 'app/shared/model/customers.model';
import { CustomersService } from 'app/entities/customers';

@Component({
    selector: 'jhi-opportunities-update',
    templateUrl: './opportunities-update.component.html'
})
export class OpportunitiesUpdateComponent implements OnInit {
    opportunities: IOpportunities;
    isSaving: boolean;

    customers: ICustomers[];
    startDate: string;
    endDate: string;

    constructor(
        private jhiAlertService: JhiAlertService,
        private opportunitiesService: OpportunitiesService,
        private customersService: CustomersService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ opportunities }) => {
            this.opportunities = opportunities;
            this.startDate = this.opportunities.startDate != null ? this.opportunities.startDate.format(DATE_TIME_FORMAT) : null;
            this.endDate = this.opportunities.endDate != null ? this.opportunities.endDate.format(DATE_TIME_FORMAT) : null;
        });
        this.customersService.query().subscribe(
            (res: HttpResponse<ICustomers[]>) => {
                this.customers = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.opportunities.startDate = this.startDate != null ? moment(this.startDate, DATE_TIME_FORMAT) : null;
        this.opportunities.endDate = this.endDate != null ? moment(this.endDate, DATE_TIME_FORMAT) : null;
        if (this.opportunities.id !== undefined) {
            this.subscribeToSaveResponse(this.opportunitiesService.update(this.opportunities));
        } else {
            this.subscribeToSaveResponse(this.opportunitiesService.create(this.opportunities));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IOpportunities>>) {
        result.subscribe((res: HttpResponse<IOpportunities>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackCustomersById(index: number, item: ICustomers) {
        return item.id;
    }
}
