package io.uranus.utility.bundle.core.utility.note;

import io.uranus.utility.bundle.core.utility.UranusUtilityEngine;
import io.uranus.utility.bundle.core.utility.chrono.helper.element.ChronoFormat;
import io.uranus.utility.bundle.core.utility.chrono.helper.element.Region;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootTest
class ExamNoteTest {

    /**
     * @see io.uranus.utility.bundle.core.utility.UranusUtilityEngine
     *
     * All units cannot be instantiated without the UranusUtilityEngine.
     */


    @Test
    void invokeDateTimeComputeChain() {
        LocalDateTime localDateTime = UranusUtilityEngine.chrono().dateTimeComputeChain()
                .fixYears(-1)
                .fixMonths(2)
                .fixDays(10)
                .fixHours(1)
                .fixMinutes(10)
                .fixSeconds(1)
                .compute();

        System.out.println("invokeDateTimeComputeChain " + localDateTime);
    }

    /**
     * @see io.uranus.utility.bundle.core.utility.chrono.helper.ChronoHelper
     * @see io.uranus.utility.bundle.core.utility.chrono.helper.calculator.LocalDateCalculator
     *
     * @implNote Invoke computation chain for create LocalDate
     */
    @Test
    void invokeDateComputeChain() {
        LocalDate date = UranusUtilityEngine.chrono().dateComputeChain()
                .fixYears(1)
                .fixMonths(2)
                .fixDays(10)
                .compute();

        System.out.println("invokeDateComputeChain " + date);
    }

    /**
     * @see io.uranus.utility.bundle.core.utility.chrono.helper.ChronoHelper
     * @see io.uranus.utility.bundle.core.utility.chrono.helper.calculator.LocalDateTimeCalculator
     *
     * @implNote Invoke computation chain with region for create LocalDateTime
     */
    @Test
    void invokeDateTImeComputeChainWithRegion() {
        LocalDateTime localDateTime = UranusUtilityEngine.chrono().dateTimeComputeChain(Region.REPUBLIC_OF_KOREA)
                .fixYears(-1)
                .fixMonths(2)
                .fixDays(10)
                .fixHours(1)
                .fixMinutes(10)
                .fixSeconds(1)
                .compute();

        System.out.println("invokeDateTImeComputeChainWithRegion " + localDateTime);
    }

    /**
     * @see io.uranus.utility.bundle.core.utility.chrono.helper.ChronoHelper
     * @see io.uranus.utility.bundle.core.utility.chrono.helper.calculator.LocalDateCalculator
     *
     * @implNote Invoke computation chain with region for create LocalDate
     */
    @Test
    void invokeDateComputeChainWithRegion() {
        LocalDate localDate = UranusUtilityEngine.chrono().dateComputeChain(Region.REPUBLIC_OF_KOREA)
                .fixYears(-1)
                .fixMonths(2)
                .fixDays(10)
                .compute();

        System.out.println("invokeDateComputeChainWithRegion " + localDate);
    }

    /**
     * @see io.uranus.utility.bundle.core.utility.chrono.helper.ChronoHelper
     *
     * @implNote create now with default region(timezone)
     */
    @Test
    void createNowWithDefaultRegion() {
        LocalDateTime nowWithNoneParam = UranusUtilityEngine.chrono().now();

        LocalDateTime nowWithNullParam = UranusUtilityEngine.chrono().now(null);

        System.out.println("createNowWithDefaultRegion " + nowWithNoneParam);
        System.out.println("createNowWithDefaultRegion " + nowWithNullParam);
    }

    /**
     * @see io.uranus.utility.bundle.core.utility.chrono.helper.ChronoHelper
     *
     * @implNote create now with region(timezone)
     */
    @Test
    void createNowWithRegion() {
        LocalDateTime now = UranusUtilityEngine.chrono().now(Region.REPUBLIC_OF_KOREA);

        System.out.println("createNowWithRegion " + now);
    }

    /**
     * @see io.uranus.utility.bundle.core.utility.chrono.helper.ChronoHelper
     *
     * @implNote create epoch of now with default region
     */
    @Test
    void createEpochWithDefaultRegion() {
        Long epochWithNoneParam = UranusUtilityEngine.chrono().epoch();
        LocalDateTime epochWithNullParam = UranusUtilityEngine.chrono().now(null);

        System.out.println("createEpochWithDefaultRegion " + epochWithNoneParam);
        System.out.println("createEpochWithDefaultRegion " + epochWithNullParam);
    }

    /**
     * @see io.uranus.utility.bundle.core.utility.chrono.helper.ChronoHelper
     *
     * @implNote create epoch of now with region(timezone)
     */
    @Test
    void createEpochWithRegion() {
        Long epochWithRegion = UranusUtilityEngine.chrono().epoch(Region.REPUBLIC_OF_KOREA);

        System.out.println("createEpochWithDefaultRegion " + epochWithRegion);
    }

    /**
     * @see io.uranus.utility.bundle.core.utility.chrono.helper.ChronoHelper
     * @see io.uranus.utility.bundle.core.utility.chrono.helper.format.ChronoFormatTransformerSelector
     * @see io.uranus.utility.bundle.core.utility.chrono.helper.format.transformer.EpochToStringTransformer
     *
     * @implNote Transform epoch to String datetime format
     */
    @Test
    void transformEpochToString() {
        String transform = UranusUtilityEngine.chrono().formatTransform().epochToString()
                .withEpoch(1739696342L)
                .withFormat(ChronoFormat.DEFAULT_FORMAT)
                .withRegion(Region.REPUBLIC_OF_KOREA)
                .transform();

        System.out.println("transformEpochToString " + transform);
    }

    /**
     * @see io.uranus.utility.bundle.core.utility.chrono.helper.ChronoHelper
     * @see io.uranus.utility.bundle.core.utility.chrono.helper.format.ChronoFormatTransformerSelector
     * @see io.uranus.utility.bundle.core.utility.chrono.helper.format.transformer.StringToEpochTransformer
     *
     * @implNote Transform validated String to epoch datetime format
     */
    @Test
    void transformStringToEpoch() {
        Long transform = UranusUtilityEngine.chrono().formatTransform().stringToEpoch()
                .withTarget("2025-02-16 18:08:01")
                .withRegion(Region.REPUBLIC_OF_KOREA)
                .transform();

        System.out.println("transformStringToEpoch " + transform);
    }

    /**
     * @see io.uranus.utility.bundle.core.utility.chrono.helper.ChronoHelper
     * @see io.uranus.utility.bundle.core.utility.chrono.helper.format.ChronoFormatTransformerSelector
     * @see io.uranus.utility.bundle.core.utility.chrono.helper.format.transformer.StringToStringTransformer
     *
     * @implNote Transform validated String format to String datetime format
     */
    @Test
    void transformStringToString() {
        String transform = UranusUtilityEngine.chrono().formatTransform().stringToString()
                .withTarget("2025-02-16 18:08:01")
                .withFormat(ChronoFormat.SIMPLIFY_FORMAT)
                .withRegion(Region.REPUBLIC_OF_KOREA)
                .transform();

        System.out.println("transformStringToEpoch " + transform);
    }

    /**
     * @see io.uranus.utility.bundle.core.utility.redis.helper.RedisHelper
     * @see io.uranus.utility.bundle.core.utility.redis.generator.RedisKeyGenerator
     *
     * @implNote Dynamic Redis Key generate
     */
    @Test
    void redisKeyGen() {
        String key = UranusUtilityEngine.redis().keygen()
                .defaultDelimiter()
                .baseKey("mybasekey")
                .add("this")
                .add("and")
                .add("that")
                .build();

        String directKeygen = UranusUtilityEngine.generateRedisKey("mybasekey", "this", "and", "that");
        String directDelimitedKeygen = UranusUtilityEngine.generateDelimitedRedisKey(":","mybasekey", "this", "and", "that");

        String hash = UranusUtilityEngine.redis().hashGen()
                .withDelimiter(":")
                .baseKey("mybasekey")
                .add("this")
                .add("and")
                .add("that")
                .build();

        String directHashGen = UranusUtilityEngine.generateRedisHash("mybasekey", "this", "and", "that");
        String directDelimitedHashGen = UranusUtilityEngine.generateDelimitedRedisHash(":", "mybasekey", "this", "and", "that");
    }

    @Test
    void json() {
        String exam = "This String is example.";

        UranusUtilityEngine parsed = UranusUtilityEngine.json().parserFor(UranusUtilityEngine.class)
                .withJson(exam)
                .parse();

        UranusUtilityEngine.json().elementExtraction()
                .withJson(exam)
                .navigate("field1")
                .navigate("field2")
                .extract();

        String directGeneratedKey = UranusUtilityEngine.generateRedisKey("mybasekey", "this", "and", "that");
        UranusUtilityEngine fromRedisAs = UranusUtilityEngine.getFromRedisAs(directGeneratedKey, UranusUtilityEngine.class);

        String fromValue = (String) UranusUtilityEngine.redis()
                .keygen("myubaseKey", "this", "and", "that")
                .getFromValue();

        UranusUtilityEngine parsedObject = UranusUtilityEngine.json().parserFor(UranusUtilityEngine.class)
                .withJson(fromValue)
                .parse();
    }
}