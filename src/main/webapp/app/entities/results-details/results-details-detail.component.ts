import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IResultsDetails } from 'app/shared/model/results-details.model';

@Component({
    selector: 'jhi-results-details-detail',
    templateUrl: './results-details-detail.component.html'
})
export class ResultsDetailsDetailComponent implements OnInit {
    resultsDetails: IResultsDetails;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ resultsDetails }) => {
            this.resultsDetails = resultsDetails;
        });
    }

    previousState() {
        window.history.back();
    }
}
