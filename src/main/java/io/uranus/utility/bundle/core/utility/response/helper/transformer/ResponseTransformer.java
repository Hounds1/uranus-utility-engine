package io.uranus.utility.bundle.core.utility.response.helper.transformer;

import io.uranus.utility.bundle.core.utility.response.helper.storage.ResponseTransformCacheContainer;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Map;

public class ResponseTransformer<T, R> {

    private final Class<R> returnClass;
    private T originClass;

    protected ResponseTransformer(Class<R> returnClass) {
        this.returnClass = returnClass;
    }

    protected ResponseTransformer<T, R> createInstance(Class<R> returnClass) {
        return new ResponseTransformer(returnClass);
    }

    public ResponseTransformer<T, R> withOrigin(T origin) {
        this.originClass = origin;
        return this;
    }

    public R transform() {
        if (returnClass == null || originClass == null) {
            throw new IllegalStateException("The returnClass and targetClass must not be null");
        }

        Map<String, Field> returnClassFields = ResponseTransformCacheContainer.getClassFields(returnClass);
        Map<String, Field> originClassFields = ResponseTransformCacheContainer.getClassFields(originClass.getClass());

        try {
            Constructor<R> declaredConstructor = this.returnClass.getDeclaredConstructor();
            declaredConstructor.setAccessible(true);

            R returnClass = declaredConstructor.newInstance();

            for (Map.Entry<String, Field> entry : originClassFields.entrySet()) {
                String fieldName = entry.getKey();
                Field originField = entry.getValue();
                Field returnField = returnClassFields.get(fieldName);

                if (returnField != null) {
                    Object value = originField.get(originClass);
                    returnField.set(returnClass, value);
                }
            }

            return returnClass;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
