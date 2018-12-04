/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PlataformaTestModule } from '../../../test.module';
import { ResultsDetailsDetailComponent } from 'app/entities/results-details/results-details-detail.component';
import { ResultsDetails } from 'app/shared/model/results-details.model';

describe('Component Tests', () => {
    describe('ResultsDetails Management Detail Component', () => {
        let comp: ResultsDetailsDetailComponent;
        let fixture: ComponentFixture<ResultsDetailsDetailComponent>;
        const route = ({ data: of({ resultsDetails: new ResultsDetails(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PlataformaTestModule],
                declarations: [ResultsDetailsDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ResultsDetailsDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ResultsDetailsDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.resultsDetails).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
