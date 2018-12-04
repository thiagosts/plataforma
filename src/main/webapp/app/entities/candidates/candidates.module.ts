import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PlataformaSharedModule } from 'app/shared';
import {
    CandidatesComponent,
    CandidatesDetailComponent,
    CandidatesUpdateComponent,
    CandidatesDeletePopupComponent,
    CandidatesDeleteDialogComponent,
    candidatesRoute,
    candidatesPopupRoute
} from './';

const ENTITY_STATES = [...candidatesRoute, ...candidatesPopupRoute];

@NgModule({
    imports: [PlataformaSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        CandidatesComponent,
        CandidatesDetailComponent,
        CandidatesUpdateComponent,
        CandidatesDeleteDialogComponent,
        CandidatesDeletePopupComponent
    ],
    entryComponents: [CandidatesComponent, CandidatesUpdateComponent, CandidatesDeleteDialogComponent, CandidatesDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PlataformaCandidatesModule {}
