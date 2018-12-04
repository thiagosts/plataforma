/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PlataformaTestModule } from '../../../test.module';
import { MatchingsJobDetailComponent } from 'app/entities/matchings-job/matchings-job-detail.component';
import { MatchingsJob } from 'app/shared/model/matchings-job.model';

describe('Component Tests', () => {
    describe('MatchingsJob Management Detail Component', () => {
        let comp: MatchingsJobDetailComponent;
        let fixture: ComponentFixture<MatchingsJobDetailComponent>;
        const route = ({ data: of({ matchingsJob: new MatchingsJob(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PlataformaTestModule],
                declarations: [MatchingsJobDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(MatchingsJobDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(MatchingsJobDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.matchingsJob).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
