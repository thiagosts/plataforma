/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { PlataformaTestModule } from '../../../test.module';
import { OpportunitiesUpdateComponent } from 'app/entities/opportunities/opportunities-update.component';
import { OpportunitiesService } from 'app/entities/opportunities/opportunities.service';
import { Opportunities } from 'app/shared/model/opportunities.model';

describe('Component Tests', () => {
    describe('Opportunities Management Update Component', () => {
        let comp: OpportunitiesUpdateComponent;
        let fixture: ComponentFixture<OpportunitiesUpdateComponent>;
        let service: OpportunitiesService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PlataformaTestModule],
                declarations: [OpportunitiesUpdateComponent]
            })
                .overrideTemplate(OpportunitiesUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(OpportunitiesUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OpportunitiesService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Opportunities(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.opportunities = entity;
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
                    const entity = new Opportunities();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.opportunities = entity;
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
