package io.uranus.utility.bundle.core.utility.json.helper.recover;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class JsonRecoveryProcessor<T> {

    private String recoveryPath;
    private final Class<T> castType;
    private final ObjectMapper om;

    protected JsonRecoveryProcessor(ObjectMapper om, Class<T> castType) {
        this.om = om;

        if (castType == null) {
            throw new IllegalArgumentException("castType cannot be null");
        }

        this.castType = castType;
    }

    public JsonRecoveryProcessor<T> withRecoveryPath(String recoveryPath) {
        this.recoveryPath = recoveryPath;
        return this;
    }

    public List<T> recovery() {
        if (this.recoveryPath == null || this.recoveryPath.isEmpty()) {
            throw new IllegalArgumentException("recoveryPath cannot be null");
        }

        List<T> results = new ArrayList<>();

        try(FileInputStream fis = new FileInputStream(this.recoveryPath);
            JsonParser parser = new JsonFactory().createParser(fis)) {

            if (parser.nextToken() != JsonToken.START_ARRAY) {
                throw new IllegalStateException("Expected an array but found " + parser.getCurrentToken());
            }

            while (parser.nextToken() != JsonToken.END_ARRAY) {
                results.add(om.readValue(parser, castType));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return results;
    }
}
