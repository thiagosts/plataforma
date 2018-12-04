import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Opportunities } from 'app/shared/model/opportunities.model';
import { OpportunitiesService } from './opportunities.service';
import { OpportunitiesComponent } from './opportunities.component';
import { OpportunitiesDetailComponent } from './opportunities-detail.component';
import { OpportunitiesUpdateComponent } from './opportunities-update.component';
import { OpportunitiesDeletePopupComponent } from './opportunities-delete-dialog.component';
import { IOpportunities } from 'app/shared/model/opportunities.model';

@Injectable({ providedIn: 'root' })
export class OpportunitiesResolve implements Resolve<IOpportunities> {
    constructor(private service: OpportunitiesService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Opportunities> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Opportunities>) => response.ok),
                map((opportunities: HttpResponse<Opportunities>) => opportunities.body)
            );
        }
        return of(new Opportunities());
    }
}

export const opportunitiesRoute: Routes = [
    {
        path: 'opportunities',
        component: OpportunitiesComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'plataformaApp.opportunities.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'opportunities/:id/view',
        component: OpportunitiesDetailComponent,
        resolve: {
            opportunities: OpportunitiesResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'plataformaApp.opportunities.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'opportunities/new',
        component: OpportunitiesUpdateComponent,
        resolve: {
            opportunities: OpportunitiesResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'plataformaApp.opportunities.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'opportunities/:id/edit',
        component: OpportunitiesUpdateComponent,
        resolve: {
            opportunities: OpportunitiesResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'plataformaApp.opportunities.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const opportunitiesPopupRoute: Routes = [
    {
        path: 'opportunities/:id/delete',
        component: OpportunitiesDeletePopupComponent,
        resolve: {
            opportunities: OpportunitiesResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'plataformaApp.opportunities.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
