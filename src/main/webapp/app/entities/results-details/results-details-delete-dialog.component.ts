import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IResultsDetails } from 'app/shared/model/results-details.model';
import { ResultsDetailsService } from './results-details.service';

@Component({
    selector: 'jhi-results-details-delete-dialog',
    templateUrl: './results-details-delete-dialog.component.html'
})
export class ResultsDetailsDeleteDialogComponent {
    resultsDetails: IResultsDetails;

    constructor(
        private resultsDetailsService: ResultsDetailsService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.resultsDetailsService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'resultsDetailsListModification',
                content: 'Deleted an resultsDetails'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-results-details-delete-popup',
    template: ''
})
export class ResultsDetailsDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ resultsDetails }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ResultsDetailsDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.resultsDetails = resultsDetails;
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
