package io.uranus.utility.bundle.core.utility.connection.helper;

import io.uranus.utility.bundle.core.utility.connection.helper.connector.NetworkConnector;
import io.uranus.utility.bundle.core.utility.connection.helper.connector.element.phase.HttpMethodPhase;
import org.springframework.web.client.RestTemplate;

public class NetworkHelper {

    protected final RestTemplate restTemplate;

    protected NetworkHelper(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    protected static NetworkHelper getInstance(RestTemplate restTemplate) {
        return new NetworkHelper(restTemplate);
    }

    public HttpMethodPhase open() {
        return NetworkConnectorDelegate.getInstance(restTemplate);
    }

    private static class NetworkConnectorDelegate extends NetworkConnector {
        private NetworkConnectorDelegate(RestTemplate restTemplate) {
            super(restTemplate);
        }

        protected static NetworkConnector getInstance(RestTemplate restTemplate) {
            return new NetworkConnectorDelegate(restTemplate);
        }
    }
}
