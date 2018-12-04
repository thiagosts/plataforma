import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IResults } from 'app/shared/model/results.model';

@Component({
    selector: 'jhi-results-detail',
    templateUrl: './results-detail.component.html'
})
export class ResultsDetailComponent implements OnInit {
    results: IResults;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ results }) => {
            this.results = results;
        });
    }

    previousState() {
        window.history.back();
    }
}
