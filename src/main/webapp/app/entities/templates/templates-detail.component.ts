import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITemplates } from 'app/shared/model/templates.model';

@Component({
    selector: 'jhi-templates-detail',
    templateUrl: './templates-detail.component.html'
})
export class TemplatesDetailComponent implements OnInit {
    templates: ITemplates;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ templates }) => {
            this.templates = templates;
        });
    }

    previousState() {
        window.history.back();
    }
}
