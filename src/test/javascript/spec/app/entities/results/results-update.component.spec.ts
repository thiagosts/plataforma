/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { PlataformaTestModule } from '../../../test.module';
import { ResultsUpdateComponent } from 'app/entities/results/results-update.component';
import { ResultsService } from 'app/entities/results/results.service';
import { Results } from 'app/shared/model/results.model';

describe('Component Tests', () => {
    describe('Results Management Update Component', () => {
        let comp: ResultsUpdateComponent;
        let fixture: ComponentFixture<ResultsUpdateComponent>;
        let service: ResultsService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PlataformaTestModule],
                declarations: [ResultsUpdateComponent]
            })
                .overrideTemplate(ResultsUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ResultsUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ResultsService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Results(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.results = entity;
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
                    const entity = new Results();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.results = entity;
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
