package io.uranus.utility.bundle.core.utility.connection.helper.connector.element.phase;

import io.uranus.utility.bundle.core.utility.connection.helper.connector.NetworkConnector;
import org.springframework.util.MultiValueMap;

import java.util.Map;
import java.util.function.Consumer;

public interface HttpBodyMountPhase {

    FinalExecutionPhase body(String body);

    FinalExecutionPhase queryParams(Consumer<Map<String, String>> consumer);

    FinalExecutionPhase form(Consumer<MultiValueMap<String, Object>> consumer);
}
