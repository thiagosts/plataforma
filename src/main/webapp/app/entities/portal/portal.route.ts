import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Portal } from 'app/shared/model/portal.model';
import { PortalService } from './portal.service';
import { PortalComponent } from './portal.component';
import { PortalDetailComponent } from './portal-detail.component';
import { PortalUpdateComponent } from './portal-update.component';
import { PortalDeletePopupComponent } from './portal-delete-dialog.component';
import { IPortal } from 'app/shared/model/portal.model';

@Injectable({ providedIn: 'root' })
export class PortalResolve implements Resolve<IPortal> {
    constructor(private service: PortalService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Portal> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Portal>) => response.ok),
                map((portal: HttpResponse<Portal>) => portal.body)
            );
        }
        return of(new Portal());
    }
}

export const portalRoute: Routes = [
    {
        path: 'portal',
        component: PortalComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'plataformaApp.portal.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'portal/:id/view',
        component: PortalDetailComponent,
        resolve: {
            portal: PortalResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'plataformaApp.portal.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'portal/new',
        component: PortalUpdateComponent,
        resolve: {
            portal: PortalResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'plataformaApp.portal.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'portal/:id/edit',
        component: PortalUpdateComponent,
        resolve: {
            portal: PortalResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'plataformaApp.portal.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const portalPopupRoute: Routes = [
    {
        path: 'portal/:id/delete',
        component: PortalDeletePopupComponent,
        resolve: {
            portal: PortalResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'plataformaApp.portal.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
