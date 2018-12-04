import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRequests } from 'app/shared/model/requests.model';

@Component({
    selector: 'jhi-requests-detail',
    templateUrl: './requests-detail.component.html'
})
export class RequestsDetailComponent implements OnInit {
    requests: IRequests;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ requests }) => {
            this.requests = requests;
        });
    }

    previousState() {
        window.history.back();
    }
}
