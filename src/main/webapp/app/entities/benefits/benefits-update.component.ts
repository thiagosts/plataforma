import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IBenefits } from 'app/shared/model/benefits.model';
import { BenefitsService } from './benefits.service';
import { IOpportunities } from 'app/shared/model/opportunities.model';
import { OpportunitiesService } from 'app/entities/opportunities';

@Component({
    selector: 'jhi-benefits-update',
    templateUrl: './benefits-update.component.html'
})
export class BenefitsUpdateComponent implements OnInit {
    benefits: IBenefits;
    isSaving: boolean;

    opportunities: IOpportunities[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private benefitsService: BenefitsService,
        private opportunitiesService: OpportunitiesService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ benefits }) => {
            this.benefits = benefits;
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
        if (this.benefits.id !== undefined) {
            this.subscribeToSaveResponse(this.benefitsService.update(this.benefits));
        } else {
            this.subscribeToSaveResponse(this.benefitsService.create(this.benefits));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IBenefits>>) {
        result.subscribe((res: HttpResponse<IBenefits>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
