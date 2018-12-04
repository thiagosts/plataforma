/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { PlataformaTestModule } from '../../../test.module';
import { PlacesUpdateComponent } from 'app/entities/places/places-update.component';
import { PlacesService } from 'app/entities/places/places.service';
import { Places } from 'app/shared/model/places.model';

describe('Component Tests', () => {
    describe('Places Management Update Component', () => {
        let comp: PlacesUpdateComponent;
        let fixture: ComponentFixture<PlacesUpdateComponent>;
        let service: PlacesService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PlataformaTestModule],
                declarations: [PlacesUpdateComponent]
            })
                .overrideTemplate(PlacesUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PlacesUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PlacesService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Places(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.places = entity;
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
                    const entity = new Places();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.places = entity;
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
