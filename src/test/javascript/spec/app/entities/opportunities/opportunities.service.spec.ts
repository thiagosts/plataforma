/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { OpportunitiesService } from 'app/entities/opportunities/opportunities.service';
import { IOpportunities, Opportunities, OpportunitiesTypeEnums } from 'app/shared/model/opportunities.model';

describe('Service Tests', () => {
    describe('Opportunities Service', () => {
        let injector: TestBed;
        let service: OpportunitiesService;
        let httpMock: HttpTestingController;
        let elemDefault: IOpportunities;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(OpportunitiesService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new Opportunities(
                0,
                'AAAAAAA',
                OpportunitiesTypeEnums.INTERNAL,
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                false,
                'AAAAAAA',
                currentDate,
                currentDate,
                0,
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA'
            );
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        startDate: currentDate.format(DATE_TIME_FORMAT),
                        endDate: currentDate.format(DATE_TIME_FORMAT)
                    },
                    elemDefault
                );
                service
                    .find(123)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: elemDefault }));

                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should create a Opportunities', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        startDate: currentDate.format(DATE_TIME_FORMAT),
                        endDate: currentDate.format(DATE_TIME_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        startDate: currentDate,
                        endDate: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new Opportunities(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a Opportunities', async () => {
                const returnedFromService = Object.assign(
                    {
                        opportunityCode: 'BBBBBB',
                        opportunitiesType: 'BBBBBB',
                        name: 'BBBBBB',
                        status: 'BBBBBB',
                        area: 'BBBBBB',
                        externalId: 'BBBBBB',
                        highlighted: true,
                        description: 'BBBBBB',
                        startDate: currentDate.format(DATE_TIME_FORMAT),
                        endDate: currentDate.format(DATE_TIME_FORMAT),
                        quantity: 1,
                        logoDesktopUrl: 'BBBBBB',
                        logoMobileUrl: 'BBBBBB',
                        hiringType: 'BBBBBB',
                        slug: 'BBBBBB'
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        startDate: currentDate,
                        endDate: currentDate
                    },
                    returnedFromService
                );
                service
                    .update(expected)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'PUT' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should return a list of Opportunities', async () => {
                const returnedFromService = Object.assign(
                    {
                        opportunityCode: 'BBBBBB',
                        opportunitiesType: 'BBBBBB',
                        name: 'BBBBBB',
                        status: 'BBBBBB',
                        area: 'BBBBBB',
                        externalId: 'BBBBBB',
                        highlighted: true,
                        description: 'BBBBBB',
                        startDate: currentDate.format(DATE_TIME_FORMAT),
                        endDate: currentDate.format(DATE_TIME_FORMAT),
                        quantity: 1,
                        logoDesktopUrl: 'BBBBBB',
                        logoMobileUrl: 'BBBBBB',
                        hiringType: 'BBBBBB',
                        slug: 'BBBBBB'
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        startDate: currentDate,
                        endDate: currentDate
                    },
                    returnedFromService
                );
                service
                    .query(expected)
                    .pipe(
                        take(1),
                        map(resp => resp.body)
                    )
                    .subscribe(body => expect(body).toContainEqual(expected));
                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify([returnedFromService]));
                httpMock.verify();
            });

            it('should delete a Opportunities', async () => {
                const rxPromise = service.delete(123).subscribe(resp => expect(resp.ok));

                const req = httpMock.expectOne({ method: 'DELETE' });
                req.flush({ status: 200 });
            });
        });

        afterEach(() => {
            httpMock.verify();
        });
    });
});
