/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { PlataformaTestModule } from '../../../test.module';
import { CustomizationUpdateComponent } from 'app/entities/customization/customization-update.component';
import { CustomizationService } from 'app/entities/customization/customization.service';
import { Customization } from 'app/shared/model/customization.model';

describe('Component Tests', () => {
    describe('Customization Management Update Component', () => {
        let comp: CustomizationUpdateComponent;
        let fixture: ComponentFixture<CustomizationUpdateComponent>;
        let service: CustomizationService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PlataformaTestModule],
                declarations: [CustomizationUpdateComponent]
            })
                .overrideTemplate(CustomizationUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CustomizationUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CustomizationService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Customization(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.customization = entity;
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
                    const entity = new Customization();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.customization = entity;
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
