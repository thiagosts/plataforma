import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ICustomization } from 'app/shared/model/customization.model';
import { CustomizationService } from './customization.service';

@Component({
    selector: 'jhi-customization-update',
    templateUrl: './customization-update.component.html'
})
export class CustomizationUpdateComponent implements OnInit {
    customization: ICustomization;
    isSaving: boolean;

    constructor(private customizationService: CustomizationService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ customization }) => {
            this.customization = customization;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.customization.id !== undefined) {
            this.subscribeToSaveResponse(this.customizationService.update(this.customization));
        } else {
            this.subscribeToSaveResponse(this.customizationService.create(this.customization));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ICustomization>>) {
        result.subscribe((res: HttpResponse<ICustomization>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
