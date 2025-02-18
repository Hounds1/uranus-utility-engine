package io.uranus.utility.bundle.core.utility.chrono.helper.format.transformer.base;

import io.uranus.utility.bundle.core.utility.chrono.helper.element.Region;

public abstract class ChronoTransformer<T> {

    /**
     * 모든 [Transformer]의 상속 클래스가 됩니다.
     * 공통적으로 소유해야 할 필드를 보유하고 이를 설정하는 super 클래스 메소드를 가집니다.
     */
    protected Region region;

    protected ChronoTransformer<T> withRegion(Region region) {
        this.region = region;
        return this;
    }

    protected ChronoTransformer<T> withDefaultRegion() {
        this.region = Region.REPUBLIC_OF_KOREA;
        return this;
    }

    /**
     * 모든 [Transformer]가 결과물 산출을 반드시 구현하도록 강제합니다.
     */
    public abstract T transform();
}
