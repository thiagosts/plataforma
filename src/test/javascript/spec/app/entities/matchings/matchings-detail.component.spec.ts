/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PlataformaTestModule } from '../../../test.module';
import { MatchingsDetailComponent } from 'app/entities/matchings/matchings-detail.component';
import { Matchings } from 'app/shared/model/matchings.model';

describe('Component Tests', () => {
    describe('Matchings Management Detail Component', () => {
        let comp: MatchingsDetailComponent;
        let fixture: ComponentFixture<MatchingsDetailComponent>;
        const route = ({ data: of({ matchings: new Matchings(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PlataformaTestModule],
                declarations: [MatchingsDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(MatchingsDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(MatchingsDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.matchings).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
