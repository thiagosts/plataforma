import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IStatusCandidates } from 'app/shared/model/status-candidates.model';

@Component({
    selector: 'jhi-status-candidates-detail',
    templateUrl: './status-candidates-detail.component.html'
})
export class StatusCandidatesDetailComponent implements OnInit {
    statusCandidates: IStatusCandidates;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ statusCandidates }) => {
            this.statusCandidates = statusCandidates;
        });
    }

    previousState() {
        window.history.back();
    }
}
