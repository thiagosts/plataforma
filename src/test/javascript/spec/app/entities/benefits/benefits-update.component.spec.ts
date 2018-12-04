/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { PlataformaTestModule } from '../../../test.module';
import { BenefitsUpdateComponent } from 'app/entities/benefits/benefits-update.component';
import { BenefitsService } from 'app/entities/benefits/benefits.service';
import { Benefits } from 'app/shared/model/benefits.model';

describe('Component Tests', () => {
    describe('Benefits Management Update Component', () => {
        let comp: BenefitsUpdateComponent;
        let fixture: ComponentFixture<BenefitsUpdateComponent>;
        let service: BenefitsService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PlataformaTestModule],
                declarations: [BenefitsUpdateComponent]
            })
                .overrideTemplate(BenefitsUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(BenefitsUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BenefitsService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Benefits(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.benefits = entity;
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
                    const entity = new Benefits();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.benefits = entity;
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
