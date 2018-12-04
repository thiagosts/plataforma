/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PlataformaTestModule } from '../../../test.module';
import { ResultsDetailComponent } from 'app/entities/results/results-detail.component';
import { Results } from 'app/shared/model/results.model';

describe('Component Tests', () => {
    describe('Results Management Detail Component', () => {
        let comp: ResultsDetailComponent;
        let fixture: ComponentFixture<ResultsDetailComponent>;
        const route = ({ data: of({ results: new Results(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PlataformaTestModule],
                declarations: [ResultsDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ResultsDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ResultsDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.results).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
