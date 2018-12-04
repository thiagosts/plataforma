import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PlataformaSharedModule } from 'app/shared';
import {
    MatchingsComponent,
    MatchingsDetailComponent,
    MatchingsUpdateComponent,
    MatchingsDeletePopupComponent,
    MatchingsDeleteDialogComponent,
    matchingsRoute,
    matchingsPopupRoute
} from './';

const ENTITY_STATES = [...matchingsRoute, ...matchingsPopupRoute];

@NgModule({
    imports: [PlataformaSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        MatchingsComponent,
        MatchingsDetailComponent,
        MatchingsUpdateComponent,
        MatchingsDeleteDialogComponent,
        MatchingsDeletePopupComponent
    ],
    entryComponents: [MatchingsComponent, MatchingsUpdateComponent, MatchingsDeleteDialogComponent, MatchingsDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PlataformaMatchingsModule {}
