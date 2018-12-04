import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Candidates } from 'app/shared/model/candidates.model';
import { CandidatesService } from './candidates.service';
import { CandidatesComponent } from './candidates.component';
import { CandidatesDetailComponent } from './candidates-detail.component';
import { CandidatesUpdateComponent } from './candidates-update.component';
import { CandidatesDeletePopupComponent } from './candidates-delete-dialog.component';
import { ICandidates } from 'app/shared/model/candidates.model';

@Injectable({ providedIn: 'root' })
export class CandidatesResolve implements Resolve<ICandidates> {
    constructor(private service: CandidatesService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Candidates> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Candidates>) => response.ok),
                map((candidates: HttpResponse<Candidates>) => candidates.body)
            );
        }
        return of(new Candidates());
    }
}

export const candidatesRoute: Routes = [
    {
        path: 'candidates',
        component: CandidatesComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'plataformaApp.candidates.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'candidates/:id/view',
        component: CandidatesDetailComponent,
        resolve: {
            candidates: CandidatesResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'plataformaApp.candidates.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'candidates/new',
        component: CandidatesUpdateComponent,
        resolve: {
            candidates: CandidatesResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'plataformaApp.candidates.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'candidates/:id/edit',
        component: CandidatesUpdateComponent,
        resolve: {
            candidates: CandidatesResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'plataformaApp.candidates.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const candidatesPopupRoute: Routes = [
    {
        path: 'candidates/:id/delete',
        component: CandidatesDeletePopupComponent,
        resolve: {
            candidates: CandidatesResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'plataformaApp.candidates.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
