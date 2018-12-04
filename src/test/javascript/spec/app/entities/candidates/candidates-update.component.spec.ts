/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { PlataformaTestModule } from '../../../test.module';
import { CandidatesUpdateComponent } from 'app/entities/candidates/candidates-update.component';
import { CandidatesService } from 'app/entities/candidates/candidates.service';
import { Candidates } from 'app/shared/model/candidates.model';

describe('Component Tests', () => {
    describe('Candidates Management Update Component', () => {
        let comp: CandidatesUpdateComponent;
        let fixture: ComponentFixture<CandidatesUpdateComponent>;
        let service: CandidatesService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PlataformaTestModule],
                declarations: [CandidatesUpdateComponent]
            })
                .overrideTemplate(CandidatesUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CandidatesUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CandidatesService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Candidates(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.candidates = entity;
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
                    const entity = new Candidates();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.candidates = entity;
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
