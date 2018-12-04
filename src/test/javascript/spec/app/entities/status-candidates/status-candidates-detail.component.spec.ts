/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PlataformaTestModule } from '../../../test.module';
import { StatusCandidatesDetailComponent } from 'app/entities/status-candidates/status-candidates-detail.component';
import { StatusCandidates } from 'app/shared/model/status-candidates.model';

describe('Component Tests', () => {
    describe('StatusCandidates Management Detail Component', () => {
        let comp: StatusCandidatesDetailComponent;
        let fixture: ComponentFixture<StatusCandidatesDetailComponent>;
        const route = ({ data: of({ statusCandidates: new StatusCandidates(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PlataformaTestModule],
                declarations: [StatusCandidatesDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(StatusCandidatesDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(StatusCandidatesDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.statusCandidates).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
