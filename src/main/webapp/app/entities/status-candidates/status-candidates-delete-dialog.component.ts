import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IStatusCandidates } from 'app/shared/model/status-candidates.model';
import { StatusCandidatesService } from './status-candidates.service';

@Component({
    selector: 'jhi-status-candidates-delete-dialog',
    templateUrl: './status-candidates-delete-dialog.component.html'
})
export class StatusCandidatesDeleteDialogComponent {
    statusCandidates: IStatusCandidates;

    constructor(
        private statusCandidatesService: StatusCandidatesService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.statusCandidatesService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'statusCandidatesListModification',
                content: 'Deleted an statusCandidates'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-status-candidates-delete-popup',
    template: ''
})
export class StatusCandidatesDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ statusCandidates }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(StatusCandidatesDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.statusCandidates = statusCandidates;
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
