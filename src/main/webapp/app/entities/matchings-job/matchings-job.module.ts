import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PlataformaSharedModule } from 'app/shared';
import {
    MatchingsJobComponent,
    MatchingsJobDetailComponent,
    MatchingsJobUpdateComponent,
    MatchingsJobDeletePopupComponent,
    MatchingsJobDeleteDialogComponent,
    matchingsJobRoute,
    matchingsJobPopupRoute
} from './';

const ENTITY_STATES = [...matchingsJobRoute, ...matchingsJobPopupRoute];

@NgModule({
    imports: [PlataformaSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        MatchingsJobComponent,
        MatchingsJobDetailComponent,
        MatchingsJobUpdateComponent,
        MatchingsJobDeleteDialogComponent,
        MatchingsJobDeletePopupComponent
    ],
    entryComponents: [
        MatchingsJobComponent,
        MatchingsJobUpdateComponent,
        MatchingsJobDeleteDialogComponent,
        MatchingsJobDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PlataformaMatchingsJobModule {}
