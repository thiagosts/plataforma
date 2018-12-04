import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Places } from 'app/shared/model/places.model';
import { PlacesService } from './places.service';
import { PlacesComponent } from './places.component';
import { PlacesDetailComponent } from './places-detail.component';
import { PlacesUpdateComponent } from './places-update.component';
import { PlacesDeletePopupComponent } from './places-delete-dialog.component';
import { IPlaces } from 'app/shared/model/places.model';

@Injectable({ providedIn: 'root' })
export class PlacesResolve implements Resolve<IPlaces> {
    constructor(private service: PlacesService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Places> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Places>) => response.ok),
                map((places: HttpResponse<Places>) => places.body)
            );
        }
        return of(new Places());
    }
}

export const placesRoute: Routes = [
    {
        path: 'places',
        component: PlacesComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'plataformaApp.places.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'places/:id/view',
        component: PlacesDetailComponent,
        resolve: {
            places: PlacesResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'plataformaApp.places.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'places/new',
        component: PlacesUpdateComponent,
        resolve: {
            places: PlacesResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'plataformaApp.places.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'places/:id/edit',
        component: PlacesUpdateComponent,
        resolve: {
            places: PlacesResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'plataformaApp.places.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const placesPopupRoute: Routes = [
    {
        path: 'places/:id/delete',
        component: PlacesDeletePopupComponent,
        resolve: {
            places: PlacesResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'plataformaApp.places.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
