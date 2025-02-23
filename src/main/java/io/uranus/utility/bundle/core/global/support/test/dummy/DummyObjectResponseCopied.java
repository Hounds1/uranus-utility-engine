package io.uranus.utility.bundle.core.global.support.test.dummy;

import io.uranus.utility.bundle.core.utility.response.helper.annotation.MappedField;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class DummyObjectResponseCopied {

    private String someValue;
    private String someValue2;
    @MappedField(origin = "someValue3")
    private String otherNameField;
}
