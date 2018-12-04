/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { PlataformaTestModule } from '../../../test.module';
import { PortalUpdateComponent } from 'app/entities/portal/portal-update.component';
import { PortalService } from 'app/entities/portal/portal.service';
import { Portal } from 'app/shared/model/portal.model';

describe('Component Tests', () => {
    describe('Portal Management Update Component', () => {
        let comp: PortalUpdateComponent;
        let fixture: ComponentFixture<PortalUpdateComponent>;
        let service: PortalService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PlataformaTestModule],
                declarations: [PortalUpdateComponent]
            })
                .overrideTemplate(PortalUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PortalUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PortalService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Portal(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.portal = entity;
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
                    const entity = new Portal();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.portal = entity;
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
