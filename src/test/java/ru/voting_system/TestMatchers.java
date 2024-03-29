package ru.voting_system;

import org.springframework.test.web.servlet.ResultMatcher;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.voting_system.TestUtil.readListFromJsonMvcResult;

public class TestMatchers<T> {
    private final Class<T> clazz;
    private final String[] fieldsToIgnore;
    private final boolean useEquals;

    private TestMatchers(Class<T> clazz, boolean useEquals, String... fieldsToIgnore) {
        this.clazz = clazz;
        this.useEquals = useEquals;
        this.fieldsToIgnore = fieldsToIgnore;
    }

    public static <T> TestMatchers<T> useFieldComparator(Class<T> clazz, String... fieldsToIgnore) {
        return new TestMatchers<>(clazz, false, fieldsToIgnore);
    }

    public static <T> TestMatchers<T> useEquals(Class<T> clazz) {
        return new TestMatchers<>(clazz, true);
    }

    public void assertMatch(T actual, T expected) {
        if (useEquals) {
            assertThat(actual).isEqualTo(expected);
        } else {
            assertThat(actual).isEqualToIgnoringGivenFields(expected, fieldsToIgnore);
        }
    }

    public void assertMatch(Iterable<T> actual, T... expected) {
        assertMatch(actual, List.of(expected));
    }

    public void assertMatch(Iterable<T> actual, Iterable<T> excepted) {
        if (useEquals) {
            assertThat(actual).isEqualTo(excepted);
        } else {
            assertThat(actual).usingElementComparatorIgnoringFields(fieldsToIgnore).isEqualTo(excepted);
        }
    }

    public ResultMatcher contentJson(T expected) {
        return result -> assertMatch(TestUtil.readFromJsonMvcResult(result, clazz), expected);
    }

    public ResultMatcher contentJson(T... expected) {
        return contentJson(List.of(expected));
    }

    public ResultMatcher contentJson(Iterable<T> expected) {
        return result -> assertMatch(readListFromJsonMvcResult(result, clazz), expected);
    }
}
