import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Matchings } from 'app/shared/model/matchings.model';
import { MatchingsService } from './matchings.service';
import { MatchingsComponent } from './matchings.component';
import { MatchingsDetailComponent } from './matchings-detail.component';
import { MatchingsUpdateComponent } from './matchings-update.component';
import { MatchingsDeletePopupComponent } from './matchings-delete-dialog.component';
import { IMatchings } from 'app/shared/model/matchings.model';

@Injectable({ providedIn: 'root' })
export class MatchingsResolve implements Resolve<IMatchings> {
    constructor(private service: MatchingsService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Matchings> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Matchings>) => response.ok),
                map((matchings: HttpResponse<Matchings>) => matchings.body)
            );
        }
        return of(new Matchings());
    }
}

export const matchingsRoute: Routes = [
    {
        path: 'matchings',
        component: MatchingsComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'plataformaApp.matchings.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'matchings/:id/view',
        component: MatchingsDetailComponent,
        resolve: {
            matchings: MatchingsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'plataformaApp.matchings.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'matchings/new',
        component: MatchingsUpdateComponent,
        resolve: {
            matchings: MatchingsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'plataformaApp.matchings.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'matchings/:id/edit',
        component: MatchingsUpdateComponent,
        resolve: {
            matchings: MatchingsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'plataformaApp.matchings.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const matchingsPopupRoute: Routes = [
    {
        path: 'matchings/:id/delete',
        component: MatchingsDeletePopupComponent,
        resolve: {
            matchings: MatchingsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'plataformaApp.matchings.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
