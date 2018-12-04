/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PlataformaTestModule } from '../../../test.module';
import { CandidatesDetailComponent } from 'app/entities/candidates/candidates-detail.component';
import { Candidates } from 'app/shared/model/candidates.model';

describe('Component Tests', () => {
    describe('Candidates Management Detail Component', () => {
        let comp: CandidatesDetailComponent;
        let fixture: ComponentFixture<CandidatesDetailComponent>;
        const route = ({ data: of({ candidates: new Candidates(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PlataformaTestModule],
                declarations: [CandidatesDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(CandidatesDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CandidatesDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.candidates).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
