package io.uranus.utility.bundle.core.utility.response.helper;

import io.uranus.utility.bundle.core.utility.response.helper.storage.ResponseTransformCacheContainer;
import io.uranus.utility.bundle.core.utility.response.helper.transformer.ResponseTransformer;

public class ResponseHelper {

    public boolean isCachedClass(Class<?> clazz) {
        return ResponseTransformCacheContainer.isContainedClass(clazz);
    }

    protected ResponseHelper() {
    }

    protected static ResponseHelper createInstance() {
        return new ResponseHelper();
    }

    /**
     * Instance Delegation
     */
    public <T,R> ResponseTransformer<T,R> transformerFor(Class<R> returnClass) {
        return ResponseTransformerDelegate.getInstance(returnClass);
    }

    /**
     * Delegators
     */
    private static class ResponseTransformerDelegate<T, R> extends ResponseTransformer<T, R> {
        private ResponseTransformerDelegate(Class<R> clazz) {
            super(clazz);
        }

        protected static <T, R> ResponseTransformerDelegate<T, R> getInstance(Class<R> clazz) {
            return new ResponseTransformerDelegate<>(clazz);
        }
    }
}
