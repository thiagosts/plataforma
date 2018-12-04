import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IResources } from 'app/shared/model/resources.model';
import { ResourcesService } from './resources.service';
import { ITemplates } from 'app/shared/model/templates.model';
import { TemplatesService } from 'app/entities/templates';

@Component({
    selector: 'jhi-resources-update',
    templateUrl: './resources-update.component.html'
})
export class ResourcesUpdateComponent implements OnInit {
    resources: IResources;
    isSaving: boolean;

    templates: ITemplates[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private resourcesService: ResourcesService,
        private templatesService: TemplatesService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ resources }) => {
            this.resources = resources;
        });
        this.templatesService.query().subscribe(
            (res: HttpResponse<ITemplates[]>) => {
                this.templates = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.resources.id !== undefined) {
            this.subscribeToSaveResponse(this.resourcesService.update(this.resources));
        } else {
            this.subscribeToSaveResponse(this.resourcesService.create(this.resources));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IResources>>) {
        result.subscribe((res: HttpResponse<IResources>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackTemplatesById(index: number, item: ITemplates) {
        return item.id;
    }
}
