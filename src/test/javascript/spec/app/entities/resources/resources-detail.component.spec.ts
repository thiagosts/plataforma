/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PlataformaTestModule } from '../../../test.module';
import { ResourcesDetailComponent } from 'app/entities/resources/resources-detail.component';
import { Resources } from 'app/shared/model/resources.model';

describe('Component Tests', () => {
    describe('Resources Management Detail Component', () => {
        let comp: ResourcesDetailComponent;
        let fixture: ComponentFixture<ResourcesDetailComponent>;
        const route = ({ data: of({ resources: new Resources(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PlataformaTestModule],
                declarations: [ResourcesDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ResourcesDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ResourcesDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.resources).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
