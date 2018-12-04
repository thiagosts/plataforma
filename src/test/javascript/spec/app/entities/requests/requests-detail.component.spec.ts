/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PlataformaTestModule } from '../../../test.module';
import { RequestsDetailComponent } from 'app/entities/requests/requests-detail.component';
import { Requests } from 'app/shared/model/requests.model';

describe('Component Tests', () => {
    describe('Requests Management Detail Component', () => {
        let comp: RequestsDetailComponent;
        let fixture: ComponentFixture<RequestsDetailComponent>;
        const route = ({ data: of({ requests: new Requests(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PlataformaTestModule],
                declarations: [RequestsDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(RequestsDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(RequestsDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.requests).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
