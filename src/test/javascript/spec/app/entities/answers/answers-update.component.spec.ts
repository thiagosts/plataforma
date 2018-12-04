/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { PlataformaTestModule } from '../../../test.module';
import { AnswersUpdateComponent } from 'app/entities/answers/answers-update.component';
import { AnswersService } from 'app/entities/answers/answers.service';
import { Answers } from 'app/shared/model/answers.model';

describe('Component Tests', () => {
    describe('Answers Management Update Component', () => {
        let comp: AnswersUpdateComponent;
        let fixture: ComponentFixture<AnswersUpdateComponent>;
        let service: AnswersService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PlataformaTestModule],
                declarations: [AnswersUpdateComponent]
            })
                .overrideTemplate(AnswersUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(AnswersUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AnswersService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Answers(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.answers = entity;
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
                    const entity = new Answers();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.answers = entity;
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
