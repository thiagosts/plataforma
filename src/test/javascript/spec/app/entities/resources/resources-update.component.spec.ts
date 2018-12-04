/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { PlataformaTestModule } from '../../../test.module';
import { ResourcesUpdateComponent } from 'app/entities/resources/resources-update.component';
import { ResourcesService } from 'app/entities/resources/resources.service';
import { Resources } from 'app/shared/model/resources.model';

describe('Component Tests', () => {
    describe('Resources Management Update Component', () => {
        let comp: ResourcesUpdateComponent;
        let fixture: ComponentFixture<ResourcesUpdateComponent>;
        let service: ResourcesService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PlataformaTestModule],
                declarations: [ResourcesUpdateComponent]
            })
                .overrideTemplate(ResourcesUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ResourcesUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ResourcesService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Resources(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.resources = entity;
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
                    const entity = new Resources();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.resources = entity;
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
