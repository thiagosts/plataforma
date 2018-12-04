/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { PlataformaTestModule } from '../../../test.module';
import { MatchingsJobUpdateComponent } from 'app/entities/matchings-job/matchings-job-update.component';
import { MatchingsJobService } from 'app/entities/matchings-job/matchings-job.service';
import { MatchingsJob } from 'app/shared/model/matchings-job.model';

describe('Component Tests', () => {
    describe('MatchingsJob Management Update Component', () => {
        let comp: MatchingsJobUpdateComponent;
        let fixture: ComponentFixture<MatchingsJobUpdateComponent>;
        let service: MatchingsJobService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PlataformaTestModule],
                declarations: [MatchingsJobUpdateComponent]
            })
                .overrideTemplate(MatchingsJobUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(MatchingsJobUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MatchingsJobService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new MatchingsJob(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.matchingsJob = entity;
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
                    const entity = new MatchingsJob();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.matchingsJob = entity;
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
