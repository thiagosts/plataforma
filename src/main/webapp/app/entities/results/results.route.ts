import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Results } from 'app/shared/model/results.model';
import { ResultsService } from './results.service';
import { ResultsComponent } from './results.component';
import { ResultsDetailComponent } from './results-detail.component';
import { ResultsUpdateComponent } from './results-update.component';
import { ResultsDeletePopupComponent } from './results-delete-dialog.component';
import { IResults } from 'app/shared/model/results.model';

@Injectable({ providedIn: 'root' })
export class ResultsResolve implements Resolve<IResults> {
    constructor(private service: ResultsService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Results> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Results>) => response.ok),
                map((results: HttpResponse<Results>) => results.body)
            );
        }
        return of(new Results());
    }
}

export const resultsRoute: Routes = [
    {
        path: 'results',
        component: ResultsComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'plataformaApp.results.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'results/:id/view',
        component: ResultsDetailComponent,
        resolve: {
            results: ResultsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'plataformaApp.results.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'results/new',
        component: ResultsUpdateComponent,
        resolve: {
            results: ResultsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'plataformaApp.results.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'results/:id/edit',
        component: ResultsUpdateComponent,
        resolve: {
            results: ResultsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'plataformaApp.results.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const resultsPopupRoute: Routes = [
    {
        path: 'results/:id/delete',
        component: ResultsDeletePopupComponent,
        resolve: {
            results: ResultsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'plataformaApp.results.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
