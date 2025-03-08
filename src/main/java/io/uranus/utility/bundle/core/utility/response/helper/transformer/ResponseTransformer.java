package io.uranus.utility.bundle.core.utility.response.helper.transformer;

import io.uranus.utility.bundle.core.utility.response.helper.annotation.MappedField;
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
        return new ResponseTransformer<T, R>(returnClass);
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

            for (Map.Entry<String, Field> entry : returnClassFields.entrySet()) {
                String returnFieldName = entry.getKey();
                Field returnField = entry.getValue();

                if (returnField.isAnnotationPresent(MappedField.class)) {
                    MappedField mappedField = returnField.getAnnotation(MappedField.class);

                    if (mappedField.ignore()) {
                        continue;
                    }

                    Field originField = originClassFields.get(mappedField.origin());

                    if (originField != null) {
                        Object val = originField.get(this.originClass);

                        if (val != null) {
                            returnField.set(returnClass, val);
                        }
                    }
                } else {
                    Field originField = originClassFields.get(returnFieldName);

                    if (originField != null) {
                        Object val = originField.get(this.originClass);

                        if (val != null) {
                            returnField.set(returnClass, val);
                        }
                    }
                }
            }

            return returnClass;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
