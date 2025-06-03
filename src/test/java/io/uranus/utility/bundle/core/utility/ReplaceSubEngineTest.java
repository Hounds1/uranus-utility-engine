package io.uranus.utility.bundle.core.utility;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ReplaceSubEngineTest {

    @Test
    void replace() {
        String template = "Test Template {}, {}, {}";
        Object[] arguments = new Object[]{"Test1", "Test2", "Test3"};

        String result = UranusUtilityEngine.replace().replacerFor(template)
                .withArguments(arguments)
                .replace();

        System.out.println(result);
    }
}
