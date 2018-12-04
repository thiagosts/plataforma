import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { MatchingsJob } from 'app/shared/model/matchings-job.model';
import { MatchingsJobService } from './matchings-job.service';
import { MatchingsJobComponent } from './matchings-job.component';
import { MatchingsJobDetailComponent } from './matchings-job-detail.component';
import { MatchingsJobUpdateComponent } from './matchings-job-update.component';
import { MatchingsJobDeletePopupComponent } from './matchings-job-delete-dialog.component';
import { IMatchingsJob } from 'app/shared/model/matchings-job.model';

@Injectable({ providedIn: 'root' })
export class MatchingsJobResolve implements Resolve<IMatchingsJob> {
    constructor(private service: MatchingsJobService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<MatchingsJob> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<MatchingsJob>) => response.ok),
                map((matchingsJob: HttpResponse<MatchingsJob>) => matchingsJob.body)
            );
        }
        return of(new MatchingsJob());
    }
}

export const matchingsJobRoute: Routes = [
    {
        path: 'matchings-job',
        component: MatchingsJobComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'plataformaApp.matchingsJob.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'matchings-job/:id/view',
        component: MatchingsJobDetailComponent,
        resolve: {
            matchingsJob: MatchingsJobResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'plataformaApp.matchingsJob.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'matchings-job/new',
        component: MatchingsJobUpdateComponent,
        resolve: {
            matchingsJob: MatchingsJobResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'plataformaApp.matchingsJob.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'matchings-job/:id/edit',
        component: MatchingsJobUpdateComponent,
        resolve: {
            matchingsJob: MatchingsJobResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'plataformaApp.matchingsJob.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const matchingsJobPopupRoute: Routes = [
    {
        path: 'matchings-job/:id/delete',
        component: MatchingsJobDeletePopupComponent,
        resolve: {
            matchingsJob: MatchingsJobResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'plataformaApp.matchingsJob.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
