package br.com.connekt.plataforma.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of BenefitsSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class BenefitsSearchRepositoryMockConfiguration {

    @MockBean
    private BenefitsSearchRepository mockBenefitsSearchRepository;

}
