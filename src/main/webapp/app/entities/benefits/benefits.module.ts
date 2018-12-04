import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PlataformaSharedModule } from 'app/shared';
import {
    BenefitsComponent,
    BenefitsDetailComponent,
    BenefitsUpdateComponent,
    BenefitsDeletePopupComponent,
    BenefitsDeleteDialogComponent,
    benefitsRoute,
    benefitsPopupRoute
} from './';

const ENTITY_STATES = [...benefitsRoute, ...benefitsPopupRoute];

@NgModule({
    imports: [PlataformaSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        BenefitsComponent,
        BenefitsDetailComponent,
        BenefitsUpdateComponent,
        BenefitsDeleteDialogComponent,
        BenefitsDeletePopupComponent
    ],
    entryComponents: [BenefitsComponent, BenefitsUpdateComponent, BenefitsDeleteDialogComponent, BenefitsDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PlataformaBenefitsModule {}
