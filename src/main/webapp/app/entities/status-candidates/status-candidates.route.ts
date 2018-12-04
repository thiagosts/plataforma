import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StatusCandidates } from 'app/shared/model/status-candidates.model';
import { StatusCandidatesService } from './status-candidates.service';
import { StatusCandidatesComponent } from './status-candidates.component';
import { StatusCandidatesDetailComponent } from './status-candidates-detail.component';
import { StatusCandidatesUpdateComponent } from './status-candidates-update.component';
import { StatusCandidatesDeletePopupComponent } from './status-candidates-delete-dialog.component';
import { IStatusCandidates } from 'app/shared/model/status-candidates.model';

@Injectable({ providedIn: 'root' })
export class StatusCandidatesResolve implements Resolve<IStatusCandidates> {
    constructor(private service: StatusCandidatesService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<StatusCandidates> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<StatusCandidates>) => response.ok),
                map((statusCandidates: HttpResponse<StatusCandidates>) => statusCandidates.body)
            );
        }
        return of(new StatusCandidates());
    }
}

export const statusCandidatesRoute: Routes = [
    {
        path: 'status-candidates',
        component: StatusCandidatesComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'plataformaApp.statusCandidates.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'status-candidates/:id/view',
        component: StatusCandidatesDetailComponent,
        resolve: {
            statusCandidates: StatusCandidatesResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'plataformaApp.statusCandidates.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'status-candidates/new',
        component: StatusCandidatesUpdateComponent,
        resolve: {
            statusCandidates: StatusCandidatesResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'plataformaApp.statusCandidates.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'status-candidates/:id/edit',
        component: StatusCandidatesUpdateComponent,
        resolve: {
            statusCandidates: StatusCandidatesResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'plataformaApp.statusCandidates.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const statusCandidatesPopupRoute: Routes = [
    {
        path: 'status-candidates/:id/delete',
        component: StatusCandidatesDeletePopupComponent,
        resolve: {
            statusCandidates: StatusCandidatesResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'plataformaApp.statusCandidates.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
