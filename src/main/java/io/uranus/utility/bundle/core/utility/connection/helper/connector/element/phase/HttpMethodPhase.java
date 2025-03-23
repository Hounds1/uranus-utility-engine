package io.uranus.utility.bundle.core.utility.connection.helper.connector.element.phase;

public interface HttpMethodPhase {

    HttpMethodPhase https();

    UrlBindPhase get();

    UrlBindPhase post();

    UrlBindPhase patch();

    UrlBindPhase put();

    UrlBindPhase delete();

    UrlBindPhase options();

}
