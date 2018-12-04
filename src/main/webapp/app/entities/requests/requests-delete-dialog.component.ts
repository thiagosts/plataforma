import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRequests } from 'app/shared/model/requests.model';
import { RequestsService } from './requests.service';

@Component({
    selector: 'jhi-requests-delete-dialog',
    templateUrl: './requests-delete-dialog.component.html'
})
export class RequestsDeleteDialogComponent {
    requests: IRequests;

    constructor(private requestsService: RequestsService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.requestsService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'requestsListModification',
                content: 'Deleted an requests'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-requests-delete-popup',
    template: ''
})
export class RequestsDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ requests }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(RequestsDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.requests = requests;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
