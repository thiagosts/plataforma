import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PlataformaSharedModule } from 'app/shared';
import {
    PortalComponent,
    PortalDetailComponent,
    PortalUpdateComponent,
    PortalDeletePopupComponent,
    PortalDeleteDialogComponent,
    portalRoute,
    portalPopupRoute
} from './';

const ENTITY_STATES = [...portalRoute, ...portalPopupRoute];

@NgModule({
    imports: [PlataformaSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [PortalComponent, PortalDetailComponent, PortalUpdateComponent, PortalDeleteDialogComponent, PortalDeletePopupComponent],
    entryComponents: [PortalComponent, PortalUpdateComponent, PortalDeleteDialogComponent, PortalDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PlataformaPortalModule {}
