/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PlataformaTestModule } from '../../../test.module';
import { TemplatesDeleteDialogComponent } from 'app/entities/templates/templates-delete-dialog.component';
import { TemplatesService } from 'app/entities/templates/templates.service';

describe('Component Tests', () => {
    describe('Templates Management Delete Component', () => {
        let comp: TemplatesDeleteDialogComponent;
        let fixture: ComponentFixture<TemplatesDeleteDialogComponent>;
        let service: TemplatesService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PlataformaTestModule],
                declarations: [TemplatesDeleteDialogComponent]
            })
                .overrideTemplate(TemplatesDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TemplatesDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TemplatesService);
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
