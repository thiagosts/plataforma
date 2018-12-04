/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PlataformaTestModule } from '../../../test.module';
import { BenefitsDeleteDialogComponent } from 'app/entities/benefits/benefits-delete-dialog.component';
import { BenefitsService } from 'app/entities/benefits/benefits.service';

describe('Component Tests', () => {
    describe('Benefits Management Delete Component', () => {
        let comp: BenefitsDeleteDialogComponent;
        let fixture: ComponentFixture<BenefitsDeleteDialogComponent>;
        let service: BenefitsService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PlataformaTestModule],
                declarations: [BenefitsDeleteDialogComponent]
            })
                .overrideTemplate(BenefitsDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(BenefitsDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BenefitsService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
