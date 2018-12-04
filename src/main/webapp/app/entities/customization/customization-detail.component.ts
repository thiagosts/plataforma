import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICustomization } from 'app/shared/model/customization.model';

@Component({
    selector: 'jhi-customization-detail',
    templateUrl: './customization-detail.component.html'
})
export class CustomizationDetailComponent implements OnInit {
    customization: ICustomization;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ customization }) => {
            this.customization = customization;
        });
    }

    previousState() {
        window.history.back();
    }
}
