/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PlataformaTestModule } from '../../../test.module';
import { BenefitsDetailComponent } from 'app/entities/benefits/benefits-detail.component';
import { Benefits } from 'app/shared/model/benefits.model';

describe('Component Tests', () => {
    describe('Benefits Management Detail Component', () => {
        let comp: BenefitsDetailComponent;
        let fixture: ComponentFixture<BenefitsDetailComponent>;
        const route = ({ data: of({ benefits: new Benefits(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PlataformaTestModule],
                declarations: [BenefitsDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(BenefitsDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(BenefitsDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.benefits).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
