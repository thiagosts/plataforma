/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { PlataformaTestModule } from '../../../test.module';
import { StatusCandidatesUpdateComponent } from 'app/entities/status-candidates/status-candidates-update.component';
import { StatusCandidatesService } from 'app/entities/status-candidates/status-candidates.service';
import { StatusCandidates } from 'app/shared/model/status-candidates.model';

describe('Component Tests', () => {
    describe('StatusCandidates Management Update Component', () => {
        let comp: StatusCandidatesUpdateComponent;
        let fixture: ComponentFixture<StatusCandidatesUpdateComponent>;
        let service: StatusCandidatesService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PlataformaTestModule],
                declarations: [StatusCandidatesUpdateComponent]
            })
                .overrideTemplate(StatusCandidatesUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(StatusCandidatesUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(StatusCandidatesService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new StatusCandidates(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.statusCandidates = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new StatusCandidates();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.statusCandidates = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
