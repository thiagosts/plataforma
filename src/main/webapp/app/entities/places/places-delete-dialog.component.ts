import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPlaces } from 'app/shared/model/places.model';
import { PlacesService } from './places.service';

@Component({
    selector: 'jhi-places-delete-dialog',
    templateUrl: './places-delete-dialog.component.html'
})
export class PlacesDeleteDialogComponent {
    places: IPlaces;

    constructor(private placesService: PlacesService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.placesService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'placesListModification',
                content: 'Deleted an places'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-places-delete-popup',
    template: ''
})
export class PlacesDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ places }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(PlacesDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.places = places;
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
