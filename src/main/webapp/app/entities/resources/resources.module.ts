import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PlataformaSharedModule } from 'app/shared';
import {
    ResourcesComponent,
    ResourcesDetailComponent,
    ResourcesUpdateComponent,
    ResourcesDeletePopupComponent,
    ResourcesDeleteDialogComponent,
    resourcesRoute,
    resourcesPopupRoute
} from './';

const ENTITY_STATES = [...resourcesRoute, ...resourcesPopupRoute];

@NgModule({
    imports: [PlataformaSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ResourcesComponent,
        ResourcesDetailComponent,
        ResourcesUpdateComponent,
        ResourcesDeleteDialogComponent,
        ResourcesDeletePopupComponent
    ],
    entryComponents: [ResourcesComponent, ResourcesUpdateComponent, ResourcesDeleteDialogComponent, ResourcesDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PlataformaResourcesModule {}
