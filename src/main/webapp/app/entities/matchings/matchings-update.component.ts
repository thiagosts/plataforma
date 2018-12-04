import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { IMatchings } from 'app/shared/model/matchings.model';
import { MatchingsService } from './matchings.service';
import { ICustomization } from 'app/shared/model/customization.model';
import { CustomizationService } from 'app/entities/customization';
import { IMatchingsJob } from 'app/shared/model/matchings-job.model';
import { MatchingsJobService } from 'app/entities/matchings-job';

@Component({
    selector: 'jhi-matchings-update',
    templateUrl: './matchings-update.component.html'
})
export class MatchingsUpdateComponent implements OnInit {
    matchings: IMatchings;
    isSaving: boolean;

    customizations: ICustomization[];

    matchingsjobs: IMatchingsJob[];
    createdDate: string;
    lastModifiedDate: string;

    constructor(
        private jhiAlertService: JhiAlertService,
        private matchingsService: MatchingsService,
        private customizationService: CustomizationService,
        private matchingsJobService: MatchingsJobService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ matchings }) => {
            this.matchings = matchings;
            this.createdDate = this.matchings.createdDate != null ? this.matchings.createdDate.format(DATE_TIME_FORMAT) : null;
            this.lastModifiedDate =
                this.matchings.lastModifiedDate != null ? this.matchings.lastModifiedDate.format(DATE_TIME_FORMAT) : null;
        });
        this.customizationService.query({ filter: 'matchings-is-null' }).subscribe(
            (res: HttpResponse<ICustomization[]>) => {
                if (!this.matchings.customizationId) {
                    this.customizations = res.body;
                } else {
                    this.customizationService.find(this.matchings.customizationId).subscribe(
                        (subRes: HttpResponse<ICustomization>) => {
                            this.customizations = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.matchingsJobService.query().subscribe(
            (res: HttpResponse<IMatchingsJob[]>) => {
                this.matchingsjobs = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.matchings.createdDate = this.createdDate != null ? moment(this.createdDate, DATE_TIME_FORMAT) : null;
        this.matchings.lastModifiedDate = this.lastModifiedDate != null ? moment(this.lastModifiedDate, DATE_TIME_FORMAT) : null;
        if (this.matchings.id !== undefined) {
            this.subscribeToSaveResponse(this.matchingsService.update(this.matchings));
        } else {
            this.subscribeToSaveResponse(this.matchingsService.create(this.matchings));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IMatchings>>) {
        result.subscribe((res: HttpResponse<IMatchings>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackCustomizationById(index: number, item: ICustomization) {
        return item.id;
    }

    trackMatchingsJobById(index: number, item: IMatchingsJob) {
        return item.id;
    }
}
