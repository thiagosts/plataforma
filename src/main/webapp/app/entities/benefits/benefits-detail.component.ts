import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBenefits } from 'app/shared/model/benefits.model';

@Component({
    selector: 'jhi-benefits-detail',
    templateUrl: './benefits-detail.component.html'
})
export class BenefitsDetailComponent implements OnInit {
    benefits: IBenefits;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ benefits }) => {
            this.benefits = benefits;
        });
    }

    previousState() {
        window.history.back();
    }
}
