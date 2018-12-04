import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { ICandidates } from 'app/shared/model/candidates.model';
import { CandidatesService } from './candidates.service';
import { IPlaces } from 'app/shared/model/places.model';
import { PlacesService } from 'app/entities/places';

@Component({
    selector: 'jhi-candidates-update',
    templateUrl: './candidates-update.component.html'
})
export class CandidatesUpdateComponent implements OnInit {
    candidates: ICandidates;
    isSaving: boolean;

    places: IPlaces[];
    dataOfBirth: string;

    constructor(
        private jhiAlertService: JhiAlertService,
        private candidatesService: CandidatesService,
        private placesService: PlacesService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ candidates }) => {
            this.candidates = candidates;
            this.dataOfBirth = this.candidates.dataOfBirth != null ? this.candidates.dataOfBirth.format(DATE_TIME_FORMAT) : null;
        });
        this.placesService.query({ filter: 'candidates-is-null' }).subscribe(
            (res: HttpResponse<IPlaces[]>) => {
                if (!this.candidates.placesId) {
                    this.places = res.body;
                } else {
                    this.placesService.find(this.candidates.placesId).subscribe(
                        (subRes: HttpResponse<IPlaces>) => {
                            this.places = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.candidates.dataOfBirth = this.dataOfBirth != null ? moment(this.dataOfBirth, DATE_TIME_FORMAT) : null;
        if (this.candidates.id !== undefined) {
            this.subscribeToSaveResponse(this.candidatesService.update(this.candidates));
        } else {
            this.subscribeToSaveResponse(this.candidatesService.create(this.candidates));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ICandidates>>) {
        result.subscribe((res: HttpResponse<ICandidates>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackPlacesById(index: number, item: IPlaces) {
        return item.id;
    }
}
