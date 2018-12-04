import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IResults } from 'app/shared/model/results.model';
import { ResultsService } from './results.service';

@Component({
    selector: 'jhi-results-delete-dialog',
    templateUrl: './results-delete-dialog.component.html'
})
export class ResultsDeleteDialogComponent {
    results: IResults;

    constructor(private resultsService: ResultsService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.resultsService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'resultsListModification',
                content: 'Deleted an results'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-results-delete-popup',
    template: ''
})
export class ResultsDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ results }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ResultsDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.results = results;
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
