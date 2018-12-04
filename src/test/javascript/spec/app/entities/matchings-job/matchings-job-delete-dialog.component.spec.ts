/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PlataformaTestModule } from '../../../test.module';
import { MatchingsJobDeleteDialogComponent } from 'app/entities/matchings-job/matchings-job-delete-dialog.component';
import { MatchingsJobService } from 'app/entities/matchings-job/matchings-job.service';

describe('Component Tests', () => {
    describe('MatchingsJob Management Delete Component', () => {
        let comp: MatchingsJobDeleteDialogComponent;
        let fixture: ComponentFixture<MatchingsJobDeleteDialogComponent>;
        let service: MatchingsJobService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PlataformaTestModule],
                declarations: [MatchingsJobDeleteDialogComponent]
            })
                .overrideTemplate(MatchingsJobDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(MatchingsJobDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MatchingsJobService);
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
