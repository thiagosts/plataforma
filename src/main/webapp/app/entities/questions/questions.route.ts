import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Questions } from 'app/shared/model/questions.model';
import { QuestionsService } from './questions.service';
import { QuestionsComponent } from './questions.component';
import { QuestionsDetailComponent } from './questions-detail.component';
import { QuestionsUpdateComponent } from './questions-update.component';
import { QuestionsDeletePopupComponent } from './questions-delete-dialog.component';
import { IQuestions } from 'app/shared/model/questions.model';

@Injectable({ providedIn: 'root' })
export class QuestionsResolve implements Resolve<IQuestions> {
    constructor(private service: QuestionsService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Questions> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Questions>) => response.ok),
                map((questions: HttpResponse<Questions>) => questions.body)
            );
        }
        return of(new Questions());
    }
}

export const questionsRoute: Routes = [
    {
        path: 'questions',
        component: QuestionsComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'plataformaApp.questions.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'questions/:id/view',
        component: QuestionsDetailComponent,
        resolve: {
            questions: QuestionsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'plataformaApp.questions.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'questions/new',
        component: QuestionsUpdateComponent,
        resolve: {
            questions: QuestionsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'plataformaApp.questions.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'questions/:id/edit',
        component: QuestionsUpdateComponent,
        resolve: {
            questions: QuestionsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'plataformaApp.questions.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const questionsPopupRoute: Routes = [
    {
        path: 'questions/:id/delete',
        component: QuestionsDeletePopupComponent,
        resolve: {
            questions: QuestionsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'plataformaApp.questions.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
