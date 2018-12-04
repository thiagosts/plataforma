/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PlataformaTestModule } from '../../../test.module';
import { CustomizationDetailComponent } from 'app/entities/customization/customization-detail.component';
import { Customization } from 'app/shared/model/customization.model';

describe('Component Tests', () => {
    describe('Customization Management Detail Component', () => {
        let comp: CustomizationDetailComponent;
        let fixture: ComponentFixture<CustomizationDetailComponent>;
        const route = ({ data: of({ customization: new Customization(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PlataformaTestModule],
                declarations: [CustomizationDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(CustomizationDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CustomizationDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.customization).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
