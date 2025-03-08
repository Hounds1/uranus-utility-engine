package io.uranus.utility.bundle.core.global.support.test.dummy;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class DummyObject {

    private String someValue;
    private String someValue2;
    private String someValue3;
    private String ignoreField;

    public static DummyObject createObject() {
        return new DummyObject("someValue", "someValue2", "someValue3", "ignoreField");
    }

}
