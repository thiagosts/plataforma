import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ResultsDetails } from 'app/shared/model/results-details.model';
import { ResultsDetailsService } from './results-details.service';
import { ResultsDetailsComponent } from './results-details.component';
import { ResultsDetailsDetailComponent } from './results-details-detail.component';
import { ResultsDetailsUpdateComponent } from './results-details-update.component';
import { ResultsDetailsDeletePopupComponent } from './results-details-delete-dialog.component';
import { IResultsDetails } from 'app/shared/model/results-details.model';

@Injectable({ providedIn: 'root' })
export class ResultsDetailsResolve implements Resolve<IResultsDetails> {
    constructor(private service: ResultsDetailsService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ResultsDetails> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<ResultsDetails>) => response.ok),
                map((resultsDetails: HttpResponse<ResultsDetails>) => resultsDetails.body)
            );
        }
        return of(new ResultsDetails());
    }
}

export const resultsDetailsRoute: Routes = [
    {
        path: 'results-details',
        component: ResultsDetailsComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'plataformaApp.resultsDetails.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'results-details/:id/view',
        component: ResultsDetailsDetailComponent,
        resolve: {
            resultsDetails: ResultsDetailsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'plataformaApp.resultsDetails.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'results-details/new',
        component: ResultsDetailsUpdateComponent,
        resolve: {
            resultsDetails: ResultsDetailsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'plataformaApp.resultsDetails.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'results-details/:id/edit',
        component: ResultsDetailsUpdateComponent,
        resolve: {
            resultsDetails: ResultsDetailsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'plataformaApp.resultsDetails.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const resultsDetailsPopupRoute: Routes = [
    {
        path: 'results-details/:id/delete',
        component: ResultsDetailsDeletePopupComponent,
        resolve: {
            resultsDetails: ResultsDetailsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'plataformaApp.resultsDetails.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
