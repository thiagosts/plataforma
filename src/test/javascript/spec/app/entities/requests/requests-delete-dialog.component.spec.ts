/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PlataformaTestModule } from '../../../test.module';
import { RequestsDeleteDialogComponent } from 'app/entities/requests/requests-delete-dialog.component';
import { RequestsService } from 'app/entities/requests/requests.service';

describe('Component Tests', () => {
    describe('Requests Management Delete Component', () => {
        let comp: RequestsDeleteDialogComponent;
        let fixture: ComponentFixture<RequestsDeleteDialogComponent>;
        let service: RequestsService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PlataformaTestModule],
                declarations: [RequestsDeleteDialogComponent]
            })
                .overrideTemplate(RequestsDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(RequestsDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RequestsService);
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
