import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Resources } from 'app/shared/model/resources.model';
import { ResourcesService } from './resources.service';
import { ResourcesComponent } from './resources.component';
import { ResourcesDetailComponent } from './resources-detail.component';
import { ResourcesUpdateComponent } from './resources-update.component';
import { ResourcesDeletePopupComponent } from './resources-delete-dialog.component';
import { IResources } from 'app/shared/model/resources.model';

@Injectable({ providedIn: 'root' })
export class ResourcesResolve implements Resolve<IResources> {
    constructor(private service: ResourcesService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Resources> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Resources>) => response.ok),
                map((resources: HttpResponse<Resources>) => resources.body)
            );
        }
        return of(new Resources());
    }
}

export const resourcesRoute: Routes = [
    {
        path: 'resources',
        component: ResourcesComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'plataformaApp.resources.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'resources/:id/view',
        component: ResourcesDetailComponent,
        resolve: {
            resources: ResourcesResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'plataformaApp.resources.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'resources/new',
        component: ResourcesUpdateComponent,
        resolve: {
            resources: ResourcesResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'plataformaApp.resources.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'resources/:id/edit',
        component: ResourcesUpdateComponent,
        resolve: {
            resources: ResourcesResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'plataformaApp.resources.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const resourcesPopupRoute: Routes = [
    {
        path: 'resources/:id/delete',
        component: ResourcesDeletePopupComponent,
        resolve: {
            resources: ResourcesResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'plataformaApp.resources.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
