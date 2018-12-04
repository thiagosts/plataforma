import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IMatchingsJob } from 'app/shared/model/matchings-job.model';
import { MatchingsJobService } from './matchings-job.service';
import { IOpportunities } from 'app/shared/model/opportunities.model';
import { OpportunitiesService } from 'app/entities/opportunities';

@Component({
    selector: 'jhi-matchings-job-update',
    templateUrl: './matchings-job-update.component.html'
})
export class MatchingsJobUpdateComponent implements OnInit {
    matchingsJob: IMatchingsJob;
    isSaving: boolean;

    opportunities: IOpportunities[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private matchingsJobService: MatchingsJobService,
        private opportunitiesService: OpportunitiesService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ matchingsJob }) => {
            this.matchingsJob = matchingsJob;
        });
        this.opportunitiesService.query({ filter: 'matchingsjob-is-null' }).subscribe(
            (res: HttpResponse<IOpportunities[]>) => {
                if (!this.matchingsJob.opportunitiesId) {
                    this.opportunities = res.body;
                } else {
                    this.opportunitiesService.find(this.matchingsJob.opportunitiesId).subscribe(
                        (subRes: HttpResponse<IOpportunities>) => {
                            this.opportunities = [subRes.body].concat(res.body);
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
        if (this.matchingsJob.id !== undefined) {
            this.subscribeToSaveResponse(this.matchingsJobService.update(this.matchingsJob));
        } else {
            this.subscribeToSaveResponse(this.matchingsJobService.create(this.matchingsJob));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IMatchingsJob>>) {
        result.subscribe((res: HttpResponse<IMatchingsJob>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
