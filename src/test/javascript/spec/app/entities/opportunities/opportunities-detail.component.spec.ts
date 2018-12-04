/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PlataformaTestModule } from '../../../test.module';
import { OpportunitiesDetailComponent } from 'app/entities/opportunities/opportunities-detail.component';
import { Opportunities } from 'app/shared/model/opportunities.model';

describe('Component Tests', () => {
    describe('Opportunities Management Detail Component', () => {
        let comp: OpportunitiesDetailComponent;
        let fixture: ComponentFixture<OpportunitiesDetailComponent>;
        const route = ({ data: of({ opportunities: new Opportunities(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PlataformaTestModule],
                declarations: [OpportunitiesDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(OpportunitiesDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(OpportunitiesDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.opportunities).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
