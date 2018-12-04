/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PlataformaTestModule } from '../../../test.module';
import { ResultsDeleteDialogComponent } from 'app/entities/results/results-delete-dialog.component';
import { ResultsService } from 'app/entities/results/results.service';

describe('Component Tests', () => {
    describe('Results Management Delete Component', () => {
        let comp: ResultsDeleteDialogComponent;
        let fixture: ComponentFixture<ResultsDeleteDialogComponent>;
        let service: ResultsService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PlataformaTestModule],
                declarations: [ResultsDeleteDialogComponent]
            })
                .overrideTemplate(ResultsDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ResultsDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ResultsService);
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
