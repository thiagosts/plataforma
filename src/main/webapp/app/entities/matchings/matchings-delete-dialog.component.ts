import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMatchings } from 'app/shared/model/matchings.model';
import { MatchingsService } from './matchings.service';

@Component({
    selector: 'jhi-matchings-delete-dialog',
    templateUrl: './matchings-delete-dialog.component.html'
})
export class MatchingsDeleteDialogComponent {
    matchings: IMatchings;

    constructor(private matchingsService: MatchingsService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.matchingsService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'matchingsListModification',
                content: 'Deleted an matchings'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-matchings-delete-popup',
    template: ''
})
export class MatchingsDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ matchings }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(MatchingsDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.matchings = matchings;
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
