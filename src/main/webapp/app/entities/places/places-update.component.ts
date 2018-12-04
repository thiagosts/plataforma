import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IPlaces } from 'app/shared/model/places.model';
import { PlacesService } from './places.service';
import { IOpportunities } from 'app/shared/model/opportunities.model';
import { OpportunitiesService } from 'app/entities/opportunities';

@Component({
    selector: 'jhi-places-update',
    templateUrl: './places-update.component.html'
})
export class PlacesUpdateComponent implements OnInit {
    places: IPlaces;
    isSaving: boolean;

    opportunities: IOpportunities[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private placesService: PlacesService,
        private opportunitiesService: OpportunitiesService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ places }) => {
            this.places = places;
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
        if (this.places.id !== undefined) {
            this.subscribeToSaveResponse(this.placesService.update(this.places));
        } else {
            this.subscribeToSaveResponse(this.placesService.create(this.places));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IPlaces>>) {
        result.subscribe((res: HttpResponse<IPlaces>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
