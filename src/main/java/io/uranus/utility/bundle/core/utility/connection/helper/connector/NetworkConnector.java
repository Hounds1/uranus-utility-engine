package io.uranus.utility.bundle.core.utility.connection.helper.connector;

import io.uranus.utility.bundle.core.utility.connection.helper.connector.element.phase.*;
import io.uranus.utility.bundle.core.utility.connection.helper.connector.element.result.ExternalNetworkResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.function.Consumer;

public class NetworkConnector implements HttpMethodPhase,
        UrlBindPhase,
        HttpHeaderBindPhase,
        HttpBodyMountPhase,
        FinalExecutionPhase {

    private final RestTemplate restTemplate;
    private final ConnectionPipeline<NetworkConnector> pipeline = new ConnectionPipeline<>();

    private boolean useHttps = false;
    private String URL;
    private HttpMethod httpMethod;
    private final MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
    private final MultiValueMap<String, Object> form = new LinkedMultiValueMap<>();
    private final Map<String, String> queryParams = new LinkedHashMap<>();
    private String body;


    protected NetworkConnector(RestTemplate restTemplate) {
        if (restTemplate == null) {
            throw new IllegalArgumentException("restTemplate cannot be null");
        }

        this.restTemplate = restTemplate;
    }

    protected static NetworkConnector createInstance(RestTemplate restTemplate) {
        return new NetworkConnector(restTemplate);
    }

    @Override
    public HttpMethodPhase https() {
        pipeline.add(c -> c.useHttps = true);

        return this;
    }

    @Override
    public UrlBindPhase get() {
        pipeline.add(c -> c.httpMethod = HttpMethod.GET);

        return this;
    }

    @Override
    public UrlBindPhase post() {
        pipeline.add(c -> c.httpMethod = HttpMethod.POST);

        return this;
    }

    @Override
    public UrlBindPhase patch() {
        pipeline.add(c -> c.httpMethod = HttpMethod.PATCH);

        return this;
    }

    @Override
    public UrlBindPhase put() {
        pipeline.add(c -> c.httpMethod = HttpMethod.PUT);

        return this;
    }

    @Override
    public UrlBindPhase delete() {
        pipeline.add(c -> c.httpMethod = HttpMethod.DELETE);

        return this;
    }

    @Override
    public UrlBindPhase options() {
        pipeline.add(c -> c.httpMethod = HttpMethod.OPTIONS);

        return this;
    }

    @Override
    public HttpHeaderBindPhase url(String url) {
        pipeline.add(c -> c.URL = url);

        return this;
    }

    @Override
    public HttpBodyMountPhase headers(Consumer<MultiValueMap<String, String>> consumer) {
        pipeline.add(c -> consumer.accept(c.headers));

        return this;
    }

    @Override
    public FinalExecutionPhase body(String body) {
        pipeline.add(c -> c.body = body);

        return this;
    }

    @Override
    public FinalExecutionPhase queryParams(Consumer<Map<String, String>> consumer) {
        pipeline.add(c -> consumer.accept(c.queryParams));

        return this;
    }

    @Override
    public FinalExecutionPhase form(Consumer<MultiValueMap<String, Object>> consumer) {
        pipeline.add(c -> consumer.accept(c.form));

        return this;
    }

    @Override
    public ExternalNetworkResponse execute() {
        pipeline.execute(this);

        if (this.useHttps && !this.URL.startsWith("https")) {
            this.URL = this.URL.replace("http", "https");
        }

        if (this.httpMethod == HttpMethod.GET) {
            if (!this.queryParams.isEmpty()) {
                if (this.URL.endsWith("/")) {
                    this.URL = this.URL.substring(0, this.URL.length() - 1);
                }

                if (!this.URL.endsWith("?")) {
                    this.URL = this.URL + "?";
                }

                StringJoiner joiner = new StringJoiner("&");

                for (Map.Entry<String, String> entry : this.queryParams.entrySet()) {
                    joiner.add(entry.getKey() + "=" + entry.getValue());
                }

                this.URL = this.URL + joiner;
            }

            return queryParamConnection();
        }

        if (this.httpMethod == HttpMethod.POST) {
            if (this.form.isEmpty() && this.body != null) {
                return bodyConnection();
            }

            if (!this.form.isEmpty() && this.body == null) {
                return formConnection();
            }
        }

        if (this.httpMethod == HttpMethod.PATCH || this.httpMethod == HttpMethod.PUT || this.httpMethod == HttpMethod.DELETE) {
            return bodyConnection();
        }

        throw new RuntimeException("Can't handle http connection.");
    }

    private ExternalNetworkResponse queryParamConnection() {
        HttpEntity<Object> httpEntity = new HttpEntity<>(this.headers);

        try {
            ResponseEntity<String> exchange = restTemplate.exchange(this.URL,
                    this.httpMethod,
                    httpEntity,
                    String.class);

            return ExternalNetworkResponse.create(exchange.getStatusCode(), exchange.getBody());
        } catch (RestClientException e) {
            throw new RuntimeException(e);
        }
    }

    private ExternalNetworkResponse bodyConnection() {
        if (this.body != null && !this.body.isEmpty() && !this.body.isBlank()) {
            HttpEntity<String> httpEntity = new HttpEntity<>(this.body, this.headers);

            try {
                ResponseEntity<String> exchange = restTemplate.exchange(this.URL,
                        this.httpMethod,
                        httpEntity,
                        String.class);

                return ExternalNetworkResponse.create(exchange.getStatusCode(), exchange.getBody());
            } catch (RestClientException e) {
                throw new RuntimeException(e);
            }
        }

        HttpEntity<String> httpEntity = new HttpEntity<>(this.headers);

        try {
            ResponseEntity<String> exchange = restTemplate.exchange(this.URL,
                    this.httpMethod,
                    httpEntity,
                    String.class);

            return ExternalNetworkResponse.create(exchange.getStatusCode(), exchange.getBody());
        } catch (RestClientException e) {
            throw new RuntimeException(e);
        }
    }

    private ExternalNetworkResponse formConnection() {
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(this.form, this.headers);

        try {
            ResponseEntity<String> exchange = restTemplate.exchange(this.URL,
                    this.httpMethod,
                    httpEntity,
                    String.class);

            return ExternalNetworkResponse.create(exchange.getStatusCode(), exchange.getBody());
        } catch (RestClientException e) {
            throw new RuntimeException(e);
        }
    }

    protected static class ConnectionPipeline<T> {
        private final List<PipelineElement<T>> pipeline = new LinkedList<>();

        public void add(PipelineElement<T> element) {
            this.pipeline.add(element);
        }

        public void execute(T input) {
            for (PipelineElement<T> item : pipeline) {
                item.apply(input);
            }
        }
    }

    @FunctionalInterface
    protected interface PipelineElement<T> {
        void apply(T element);
    }
}
