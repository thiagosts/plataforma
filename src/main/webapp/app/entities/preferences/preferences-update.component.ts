import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IPreferences } from 'app/shared/model/preferences.model';
import { PreferencesService } from './preferences.service';
import { ICandidates } from 'app/shared/model/candidates.model';
import { CandidatesService } from 'app/entities/candidates';

@Component({
    selector: 'jhi-preferences-update',
    templateUrl: './preferences-update.component.html'
})
export class PreferencesUpdateComponent implements OnInit {
    preferences: IPreferences;
    isSaving: boolean;

    candidates: ICandidates[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private preferencesService: PreferencesService,
        private candidatesService: CandidatesService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ preferences }) => {
            this.preferences = preferences;
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
        if (this.preferences.id !== undefined) {
            this.subscribeToSaveResponse(this.preferencesService.update(this.preferences));
        } else {
            this.subscribeToSaveResponse(this.preferencesService.create(this.preferences));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IPreferences>>) {
        result.subscribe((res: HttpResponse<IPreferences>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
