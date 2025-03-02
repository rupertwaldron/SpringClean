package com.ruppyrup.springclean.integrationtests;

import com.ruppyrup.springclean.controllers.TranslationController;
import com.ruppyrup.springclean.dto.TranslationRequest;
import com.ruppyrup.springclean.service.TranslationService;
import com.ruppyrup.springclean.whatfactories.FrenchTranslator;
import com.ruppyrup.springclean.whatfactories.Translator;
import com.ruppyrup.springclean.whatfactories.TranslatorConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@Import(IntegrationTestConfig.class)
@ExtendWith(LoggingExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CachingTest {

    public static final String TEXT_TO_TRANSLATE = "Text to translate";
    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;

    @MockitoSpyBean
    private TranslationService translationService;

    private HttpHeaders headers;

    @BeforeEach
    void beforeEach() {
        List<MediaType> mediaTypes = new ArrayList<>();
        mediaTypes.add(MediaType.APPLICATION_JSON);
        headers = new HttpHeaders();
        headers.setAccept(mediaTypes);
    }

    @Test
    void translateInputToFrench() {
        TranslationRequest body = new TranslationRequest(TEXT_TO_TRANSLATE, "french");

        HttpEntity<TranslationRequest> entity = new HttpEntity<>(body, headers);
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("localhost")
                .port(port)
                .path("translate")
                .build();

        ResponseEntity<String> response = restTemplate.postForEntity(uriComponents.toUriString(), entity, String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), containsString("French translation of \"Text to translate\""));

        response = restTemplate.postForEntity(uriComponents.toUriString(), entity, String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), containsString("French translation of \"Text to translate\""));

        response = restTemplate.postForEntity(uriComponents.toUriString(), entity, String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), containsString("French translation of \"Text to translate\""));

        verify(translationService, times(1)).translate(TEXT_TO_TRANSLATE);
    }
}
