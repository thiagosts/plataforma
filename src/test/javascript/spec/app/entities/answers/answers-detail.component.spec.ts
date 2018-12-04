/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PlataformaTestModule } from '../../../test.module';
import { AnswersDetailComponent } from 'app/entities/answers/answers-detail.component';
import { Answers } from 'app/shared/model/answers.model';

describe('Component Tests', () => {
    describe('Answers Management Detail Component', () => {
        let comp: AnswersDetailComponent;
        let fixture: ComponentFixture<AnswersDetailComponent>;
        const route = ({ data: of({ answers: new Answers(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PlataformaTestModule],
                declarations: [AnswersDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(AnswersDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AnswersDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.answers).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
