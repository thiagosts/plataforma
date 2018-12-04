/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { PlataformaTestModule } from '../../../test.module';
import { ResultsDetailsUpdateComponent } from 'app/entities/results-details/results-details-update.component';
import { ResultsDetailsService } from 'app/entities/results-details/results-details.service';
import { ResultsDetails } from 'app/shared/model/results-details.model';

describe('Component Tests', () => {
    describe('ResultsDetails Management Update Component', () => {
        let comp: ResultsDetailsUpdateComponent;
        let fixture: ComponentFixture<ResultsDetailsUpdateComponent>;
        let service: ResultsDetailsService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PlataformaTestModule],
                declarations: [ResultsDetailsUpdateComponent]
            })
                .overrideTemplate(ResultsDetailsUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ResultsDetailsUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ResultsDetailsService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new ResultsDetails(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.resultsDetails = entity;
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
                    const entity = new ResultsDetails();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.resultsDetails = entity;
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
