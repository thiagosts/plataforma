import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBenefits } from 'app/shared/model/benefits.model';
import { BenefitsService } from './benefits.service';

@Component({
    selector: 'jhi-benefits-delete-dialog',
    templateUrl: './benefits-delete-dialog.component.html'
})
export class BenefitsDeleteDialogComponent {
    benefits: IBenefits;

    constructor(private benefitsService: BenefitsService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.benefitsService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'benefitsListModification',
                content: 'Deleted an benefits'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-benefits-delete-popup',
    template: ''
})
export class BenefitsDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ benefits }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(BenefitsDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.benefits = benefits;
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
