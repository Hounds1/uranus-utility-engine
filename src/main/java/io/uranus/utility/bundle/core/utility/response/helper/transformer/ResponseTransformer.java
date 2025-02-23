package io.uranus.utility.bundle.core.utility.response.helper.transformer;

import io.uranus.utility.bundle.core.utility.response.helper.storage.ResponseTransformCacheContainer;

import java.lang.reflect.Field;
import java.util.Map;

public class ResponseTransformer<T, R> {

    private final Class<R> returnClass;
    private Class<T> originClass;

    protected ResponseTransformer(Class<R> returnClass) {
        this.returnClass = returnClass;
    }

    protected ResponseTransformer<T, R> createInstance(Class<R> returnClass) {
        return new ResponseTransformer(returnClass);
    }

    public ResponseTransformer<T, R> withOrigin(Class<T> origin) {
        this.originClass = origin;
        return this;
    }

    public R transform() {
        if (returnClass == null || originClass == null) {
            throw new IllegalStateException("The returnClass and targetClass must not be null");
        }

        Map<String, Field> returnClassFields = ResponseTransformCacheContainer.getClassFields(returnClass);
        Map<String, Field> originClassFields = ResponseTransformCacheContainer.getClassFields(originClass);

        try {
            R returnClass = this.returnClass.getDeclaredConstructor().newInstance();

            for (Map.Entry<String, Field> entry : originClassFields.entrySet()) {
                String fieldName = entry.getKey();
                Field targetField = entry.getValue();
                Field returnField = returnClassFields.get(fieldName);

                if (returnField != null) {
                    returnField.set(returnField, targetField.get(originClass));
                }
            }

            return returnClass;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
