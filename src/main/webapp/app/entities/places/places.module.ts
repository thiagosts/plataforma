import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PlataformaSharedModule } from 'app/shared';
import {
    PlacesComponent,
    PlacesDetailComponent,
    PlacesUpdateComponent,
    PlacesDeletePopupComponent,
    PlacesDeleteDialogComponent,
    placesRoute,
    placesPopupRoute
} from './';

const ENTITY_STATES = [...placesRoute, ...placesPopupRoute];

@NgModule({
    imports: [PlataformaSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [PlacesComponent, PlacesDetailComponent, PlacesUpdateComponent, PlacesDeleteDialogComponent, PlacesDeletePopupComponent],
    entryComponents: [PlacesComponent, PlacesUpdateComponent, PlacesDeleteDialogComponent, PlacesDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PlataformaPlacesModule {}
