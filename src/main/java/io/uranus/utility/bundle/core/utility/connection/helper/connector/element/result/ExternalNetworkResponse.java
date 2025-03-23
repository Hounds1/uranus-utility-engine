package io.uranus.utility.bundle.core.utility.connection.helper.connector.element.result;

import lombok.*;
import org.springframework.http.HttpStatusCode;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class ExternalNetworkResponse {

    private HttpStatusCode status;
    private String body;

    public static ExternalNetworkResponse create(HttpStatusCode status, String body) {
        return new ExternalNetworkResponse(status, body);
    }
}
