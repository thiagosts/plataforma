import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IOpportunities } from 'app/shared/model/opportunities.model';
import { OpportunitiesService } from './opportunities.service';

@Component({
    selector: 'jhi-opportunities-delete-dialog',
    templateUrl: './opportunities-delete-dialog.component.html'
})
export class OpportunitiesDeleteDialogComponent {
    opportunities: IOpportunities;

    constructor(
        private opportunitiesService: OpportunitiesService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.opportunitiesService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'opportunitiesListModification',
                content: 'Deleted an opportunities'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-opportunities-delete-popup',
    template: ''
})
export class OpportunitiesDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ opportunities }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(OpportunitiesDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.opportunities = opportunities;
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
