import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PlataformaSharedModule } from 'app/shared';
import {
    CustomizationComponent,
    CustomizationDetailComponent,
    CustomizationUpdateComponent,
    CustomizationDeletePopupComponent,
    CustomizationDeleteDialogComponent,
    customizationRoute,
    customizationPopupRoute
} from './';

const ENTITY_STATES = [...customizationRoute, ...customizationPopupRoute];

@NgModule({
    imports: [PlataformaSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        CustomizationComponent,
        CustomizationDetailComponent,
        CustomizationUpdateComponent,
        CustomizationDeleteDialogComponent,
        CustomizationDeletePopupComponent
    ],
    entryComponents: [
        CustomizationComponent,
        CustomizationUpdateComponent,
        CustomizationDeleteDialogComponent,
        CustomizationDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PlataformaCustomizationModule {}
