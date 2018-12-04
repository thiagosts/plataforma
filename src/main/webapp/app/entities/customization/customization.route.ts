import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Customization } from 'app/shared/model/customization.model';
import { CustomizationService } from './customization.service';
import { CustomizationComponent } from './customization.component';
import { CustomizationDetailComponent } from './customization-detail.component';
import { CustomizationUpdateComponent } from './customization-update.component';
import { CustomizationDeletePopupComponent } from './customization-delete-dialog.component';
import { ICustomization } from 'app/shared/model/customization.model';

@Injectable({ providedIn: 'root' })
export class CustomizationResolve implements Resolve<ICustomization> {
    constructor(private service: CustomizationService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Customization> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Customization>) => response.ok),
                map((customization: HttpResponse<Customization>) => customization.body)
            );
        }
        return of(new Customization());
    }
}

export const customizationRoute: Routes = [
    {
        path: 'customization',
        component: CustomizationComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'plataformaApp.customization.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'customization/:id/view',
        component: CustomizationDetailComponent,
        resolve: {
            customization: CustomizationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'plataformaApp.customization.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'customization/new',
        component: CustomizationUpdateComponent,
        resolve: {
            customization: CustomizationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'plataformaApp.customization.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'customization/:id/edit',
        component: CustomizationUpdateComponent,
        resolve: {
            customization: CustomizationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'plataformaApp.customization.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const customizationPopupRoute: Routes = [
    {
        path: 'customization/:id/delete',
        component: CustomizationDeletePopupComponent,
        resolve: {
            customization: CustomizationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'plataformaApp.customization.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
