import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMatchingsJob } from 'app/shared/model/matchings-job.model';
import { MatchingsJobService } from './matchings-job.service';

@Component({
    selector: 'jhi-matchings-job-delete-dialog',
    templateUrl: './matchings-job-delete-dialog.component.html'
})
export class MatchingsJobDeleteDialogComponent {
    matchingsJob: IMatchingsJob;

    constructor(
        private matchingsJobService: MatchingsJobService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.matchingsJobService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'matchingsJobListModification',
                content: 'Deleted an matchingsJob'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-matchings-job-delete-popup',
    template: ''
})
export class MatchingsJobDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ matchingsJob }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(MatchingsJobDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.matchingsJob = matchingsJob;
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
