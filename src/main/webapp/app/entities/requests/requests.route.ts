import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Requests } from 'app/shared/model/requests.model';
import { RequestsService } from './requests.service';
import { RequestsComponent } from './requests.component';
import { RequestsDetailComponent } from './requests-detail.component';
import { RequestsUpdateComponent } from './requests-update.component';
import { RequestsDeletePopupComponent } from './requests-delete-dialog.component';
import { IRequests } from 'app/shared/model/requests.model';

@Injectable({ providedIn: 'root' })
export class RequestsResolve implements Resolve<IRequests> {
    constructor(private service: RequestsService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Requests> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Requests>) => response.ok),
                map((requests: HttpResponse<Requests>) => requests.body)
            );
        }
        return of(new Requests());
    }
}

export const requestsRoute: Routes = [
    {
        path: 'requests',
        component: RequestsComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'plataformaApp.requests.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'requests/:id/view',
        component: RequestsDetailComponent,
        resolve: {
            requests: RequestsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'plataformaApp.requests.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'requests/new',
        component: RequestsUpdateComponent,
        resolve: {
            requests: RequestsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'plataformaApp.requests.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'requests/:id/edit',
        component: RequestsUpdateComponent,
        resolve: {
            requests: RequestsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'plataformaApp.requests.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const requestsPopupRoute: Routes = [
    {
        path: 'requests/:id/delete',
        component: RequestsDeletePopupComponent,
        resolve: {
            requests: RequestsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'plataformaApp.requests.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
