import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPlaces } from 'app/shared/model/places.model';

@Component({
    selector: 'jhi-places-detail',
    templateUrl: './places-detail.component.html'
})
export class PlacesDetailComponent implements OnInit {
    places: IPlaces;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ places }) => {
            this.places = places;
        });
    }

    previousState() {
        window.history.back();
    }
}
