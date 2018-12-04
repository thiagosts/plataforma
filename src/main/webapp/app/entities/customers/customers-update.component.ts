import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ICustomers } from 'app/shared/model/customers.model';
import { CustomersService } from './customers.service';

@Component({
    selector: 'jhi-customers-update',
    templateUrl: './customers-update.component.html'
})
export class CustomersUpdateComponent implements OnInit {
    customers: ICustomers;
    isSaving: boolean;

    constructor(private customersService: CustomersService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ customers }) => {
            this.customers = customers;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.customers.id !== undefined) {
            this.subscribeToSaveResponse(this.customersService.update(this.customers));
        } else {
            this.subscribeToSaveResponse(this.customersService.create(this.customers));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ICustomers>>) {
        result.subscribe((res: HttpResponse<ICustomers>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
