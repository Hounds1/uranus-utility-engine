package io.uranus.utility.bundle.core.utility.connection.helper.connector.element.phase;

import org.springframework.util.MultiValueMap;

import java.util.function.Consumer;

public interface HttpHeaderBindPhase {

    HttpBodyMountPhase headers(Consumer<MultiValueMap<String, String>> consumer);
}
