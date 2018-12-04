import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Benefits } from 'app/shared/model/benefits.model';
import { BenefitsService } from './benefits.service';
import { BenefitsComponent } from './benefits.component';
import { BenefitsDetailComponent } from './benefits-detail.component';
import { BenefitsUpdateComponent } from './benefits-update.component';
import { BenefitsDeletePopupComponent } from './benefits-delete-dialog.component';
import { IBenefits } from 'app/shared/model/benefits.model';

@Injectable({ providedIn: 'root' })
export class BenefitsResolve implements Resolve<IBenefits> {
    constructor(private service: BenefitsService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Benefits> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Benefits>) => response.ok),
                map((benefits: HttpResponse<Benefits>) => benefits.body)
            );
        }
        return of(new Benefits());
    }
}

export const benefitsRoute: Routes = [
    {
        path: 'benefits',
        component: BenefitsComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'plataformaApp.benefits.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'benefits/:id/view',
        component: BenefitsDetailComponent,
        resolve: {
            benefits: BenefitsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'plataformaApp.benefits.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'benefits/new',
        component: BenefitsUpdateComponent,
        resolve: {
            benefits: BenefitsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'plataformaApp.benefits.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'benefits/:id/edit',
        component: BenefitsUpdateComponent,
        resolve: {
            benefits: BenefitsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'plataformaApp.benefits.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const benefitsPopupRoute: Routes = [
    {
        path: 'benefits/:id/delete',
        component: BenefitsDeletePopupComponent,
        resolve: {
            benefits: BenefitsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'plataformaApp.benefits.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
