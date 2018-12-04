import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IOpportunities } from 'app/shared/model/opportunities.model';

@Component({
    selector: 'jhi-opportunities-detail',
    templateUrl: './opportunities-detail.component.html'
})
export class OpportunitiesDetailComponent implements OnInit {
    opportunities: IOpportunities;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ opportunities }) => {
            this.opportunities = opportunities;
        });
    }

    previousState() {
        window.history.back();
    }
}
