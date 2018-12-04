import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ITemplates } from 'app/shared/model/templates.model';
import { TemplatesService } from './templates.service';

@Component({
    selector: 'jhi-templates-update',
    templateUrl: './templates-update.component.html'
})
export class TemplatesUpdateComponent implements OnInit {
    templates: ITemplates;
    isSaving: boolean;

    constructor(private templatesService: TemplatesService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ templates }) => {
            this.templates = templates;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.templates.id !== undefined) {
            this.subscribeToSaveResponse(this.templatesService.update(this.templates));
        } else {
            this.subscribeToSaveResponse(this.templatesService.create(this.templates));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ITemplates>>) {
        result.subscribe((res: HttpResponse<ITemplates>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
