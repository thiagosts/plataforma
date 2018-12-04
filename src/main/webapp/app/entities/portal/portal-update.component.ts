import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IPortal } from 'app/shared/model/portal.model';
import { PortalService } from './portal.service';
import { ITemplates } from 'app/shared/model/templates.model';
import { TemplatesService } from 'app/entities/templates';
import { ICustomers } from 'app/shared/model/customers.model';
import { CustomersService } from 'app/entities/customers';

@Component({
    selector: 'jhi-portal-update',
    templateUrl: './portal-update.component.html'
})
export class PortalUpdateComponent implements OnInit {
    portal: IPortal;
    isSaving: boolean;

    templates: ITemplates[];

    customers: ICustomers[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private portalService: PortalService,
        private templatesService: TemplatesService,
        private customersService: CustomersService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ portal }) => {
            this.portal = portal;
        });
        this.templatesService.query({ filter: 'portal-is-null' }).subscribe(
            (res: HttpResponse<ITemplates[]>) => {
                if (!this.portal.templatesId) {
                    this.templates = res.body;
                } else {
                    this.templatesService.find(this.portal.templatesId).subscribe(
                        (subRes: HttpResponse<ITemplates>) => {
                            this.templates = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.customersService.query().subscribe(
            (res: HttpResponse<ICustomers[]>) => {
                this.customers = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.portal.id !== undefined) {
            this.subscribeToSaveResponse(this.portalService.update(this.portal));
        } else {
            this.subscribeToSaveResponse(this.portalService.create(this.portal));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IPortal>>) {
        result.subscribe((res: HttpResponse<IPortal>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackCustomersById(index: number, item: ICustomers) {
        return item.id;
    }
}
