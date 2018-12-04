import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMatchingsJob } from 'app/shared/model/matchings-job.model';

@Component({
    selector: 'jhi-matchings-job-detail',
    templateUrl: './matchings-job-detail.component.html'
})
export class MatchingsJobDetailComponent implements OnInit {
    matchingsJob: IMatchingsJob;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ matchingsJob }) => {
            this.matchingsJob = matchingsJob;
        });
    }

    previousState() {
        window.history.back();
    }
}
