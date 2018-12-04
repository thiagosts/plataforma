/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PlataformaTestModule } from '../../../test.module';
import { TemplatesDetailComponent } from 'app/entities/templates/templates-detail.component';
import { Templates } from 'app/shared/model/templates.model';

describe('Component Tests', () => {
    describe('Templates Management Detail Component', () => {
        let comp: TemplatesDetailComponent;
        let fixture: ComponentFixture<TemplatesDetailComponent>;
        const route = ({ data: of({ templates: new Templates(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PlataformaTestModule],
                declarations: [TemplatesDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(TemplatesDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TemplatesDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.templates).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
