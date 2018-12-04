/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { PlataformaTestModule } from '../../../test.module';
import { RequestsUpdateComponent } from 'app/entities/requests/requests-update.component';
import { RequestsService } from 'app/entities/requests/requests.service';
import { Requests } from 'app/shared/model/requests.model';

describe('Component Tests', () => {
    describe('Requests Management Update Component', () => {
        let comp: RequestsUpdateComponent;
        let fixture: ComponentFixture<RequestsUpdateComponent>;
        let service: RequestsService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PlataformaTestModule],
                declarations: [RequestsUpdateComponent]
            })
                .overrideTemplate(RequestsUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(RequestsUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RequestsService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Requests(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.requests = entity;
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
                    const entity = new Requests();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.requests = entity;
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
