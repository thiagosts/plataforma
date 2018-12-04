/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PlataformaTestModule } from '../../../test.module';
import { QuestionsDetailComponent } from 'app/entities/questions/questions-detail.component';
import { Questions } from 'app/shared/model/questions.model';

describe('Component Tests', () => {
    describe('Questions Management Detail Component', () => {
        let comp: QuestionsDetailComponent;
        let fixture: ComponentFixture<QuestionsDetailComponent>;
        const route = ({ data: of({ questions: new Questions(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PlataformaTestModule],
                declarations: [QuestionsDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(QuestionsDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(QuestionsDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.questions).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
