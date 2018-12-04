import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPortal } from 'app/shared/model/portal.model';

@Component({
    selector: 'jhi-portal-detail',
    templateUrl: './portal-detail.component.html'
})
export class PortalDetailComponent implements OnInit {
    portal: IPortal;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ portal }) => {
            this.portal = portal;
        });
    }

    previousState() {
        window.history.back();
    }
}
