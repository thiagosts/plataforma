/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PlataformaTestModule } from '../../../test.module';
import { PlacesDetailComponent } from 'app/entities/places/places-detail.component';
import { Places } from 'app/shared/model/places.model';

describe('Component Tests', () => {
    describe('Places Management Detail Component', () => {
        let comp: PlacesDetailComponent;
        let fixture: ComponentFixture<PlacesDetailComponent>;
        const route = ({ data: of({ places: new Places(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PlataformaTestModule],
                declarations: [PlacesDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(PlacesDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PlacesDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.places).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
