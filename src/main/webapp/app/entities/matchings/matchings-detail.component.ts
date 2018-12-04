import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMatchings } from 'app/shared/model/matchings.model';

@Component({
    selector: 'jhi-matchings-detail',
    templateUrl: './matchings-detail.component.html'
})
export class MatchingsDetailComponent implements OnInit {
    matchings: IMatchings;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ matchings }) => {
            this.matchings = matchings;
        });
    }

    previousState() {
        window.history.back();
    }
}
