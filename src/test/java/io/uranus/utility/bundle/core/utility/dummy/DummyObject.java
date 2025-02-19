package io.uranus.utility.bundle.core.utility.dummy;

public class DummyObject {

    private String someValue;
    private String someValue2;
    private String someValue3;

    public DummyObject(String someValue, String someValue2, String someValue3) {
        this.someValue = someValue;
        this.someValue2 = someValue2;
        this.someValue3 = someValue3;
    }

    public DummyObject() {}

    public static DummyObject createObject() {
        return new DummyObject("someValue", "someValue2", "someValue3");
    }

    public String getSomeValue() {
        return someValue;
    }

    public void setSomeValue(String someValue) {
        this.someValue = someValue;
    }

    public String getSomeValue2() {
        return someValue2;
    }

    public void setSomeValue2(String someValue2) {
        this.someValue2 = someValue2;
    }

    public String getSomeValue3() {
        return someValue3;
    }

    public void setSomeValue3(String someValue3) {
        this.someValue3 = someValue3;
    }
}
