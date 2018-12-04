import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICandidates } from 'app/shared/model/candidates.model';

@Component({
    selector: 'jhi-candidates-detail',
    templateUrl: './candidates-detail.component.html'
})
export class CandidatesDetailComponent implements OnInit {
    candidates: ICandidates;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ candidates }) => {
            this.candidates = candidates;
        });
    }

    previousState() {
        window.history.back();
    }
}
