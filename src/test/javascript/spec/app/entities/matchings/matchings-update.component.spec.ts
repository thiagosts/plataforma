/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { PlataformaTestModule } from '../../../test.module';
import { MatchingsUpdateComponent } from 'app/entities/matchings/matchings-update.component';
import { MatchingsService } from 'app/entities/matchings/matchings.service';
import { Matchings } from 'app/shared/model/matchings.model';

describe('Component Tests', () => {
    describe('Matchings Management Update Component', () => {
        let comp: MatchingsUpdateComponent;
        let fixture: ComponentFixture<MatchingsUpdateComponent>;
        let service: MatchingsService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PlataformaTestModule],
                declarations: [MatchingsUpdateComponent]
            })
                .overrideTemplate(MatchingsUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(MatchingsUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MatchingsService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Matchings(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.matchings = entity;
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
                    const entity = new Matchings();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.matchings = entity;
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
