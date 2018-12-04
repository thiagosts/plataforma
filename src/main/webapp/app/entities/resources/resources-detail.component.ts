import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IResources } from 'app/shared/model/resources.model';

@Component({
    selector: 'jhi-resources-detail',
    templateUrl: './resources-detail.component.html'
})
export class ResourcesDetailComponent implements OnInit {
    resources: IResources;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ resources }) => {
            this.resources = resources;
        });
    }

    previousState() {
        window.history.back();
    }
}
