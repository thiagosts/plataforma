import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { PlataformaOpportunitiesModule } from './opportunities/opportunities.module';
import { PlataformaRequestsModule } from './requests/requests.module';
import { PlataformaPlacesModule } from './places/places.module';
import { PlataformaBenefitsModule } from './benefits/benefits.module';
import { PlataformaCustomersModule } from './customers/customers.module';
import { PlataformaPortalModule } from './portal/portal.module';
import { PlataformaTemplatesModule } from './templates/templates.module';
import { PlataformaResourcesModule } from './resources/resources.module';
import { PlataformaCandidatesModule } from './candidates/candidates.module';
import { PlataformaPreferencesModule } from './preferences/preferences.module';
import { PlataformaStatusCandidatesModule } from './status-candidates/status-candidates.module';
import { PlataformaResultsModule } from './results/results.module';
import { PlataformaResultsDetailsModule } from './results-details/results-details.module';
import { PlataformaMatchingsJobModule } from './matchings-job/matchings-job.module';
import { PlataformaMatchingsModule } from './matchings/matchings.module';
import { PlataformaQuestionsModule } from './questions/questions.module';
import { PlataformaAnswersModule } from './answers/answers.module';
import { PlataformaCustomizationModule } from './customization/customization.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        PlataformaOpportunitiesModule,
        PlataformaRequestsModule,
        PlataformaPlacesModule,
        PlataformaBenefitsModule,
        PlataformaCustomersModule,
        PlataformaPortalModule,
        PlataformaTemplatesModule,
        PlataformaResourcesModule,
        PlataformaCandidatesModule,
        PlataformaPreferencesModule,
        PlataformaStatusCandidatesModule,
        PlataformaResultsModule,
        PlataformaResultsDetailsModule,
        PlataformaMatchingsJobModule,
        PlataformaMatchingsModule,
        PlataformaQuestionsModule,
        PlataformaAnswersModule,
        PlataformaCustomizationModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PlataformaEntityModule {}
