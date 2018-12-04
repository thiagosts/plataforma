import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PlataformaSharedModule } from 'app/shared';
import {
    RequestsComponent,
    RequestsDetailComponent,
    RequestsUpdateComponent,
    RequestsDeletePopupComponent,
    RequestsDeleteDialogComponent,
    requestsRoute,
    requestsPopupRoute
} from './';

const ENTITY_STATES = [...requestsRoute, ...requestsPopupRoute];

@NgModule({
    imports: [PlataformaSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        RequestsComponent,
        RequestsDetailComponent,
        RequestsUpdateComponent,
        RequestsDeleteDialogComponent,
        RequestsDeletePopupComponent
    ],
    entryComponents: [RequestsComponent, RequestsUpdateComponent, RequestsDeleteDialogComponent, RequestsDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PlataformaRequestsModule {}
