package org.vtsukur.algorithms.stack;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author volodymyr.tsukur
 */
@RunWith(Parameterized.class)
public class StackTest {

    private final Builder builder;

    public StackTest(final Provider provider) {
        this.builder = provider.builder;
    }

    @Parameterized.Parameters(name = "{0}")
    public static Collection<Object[]> implementations() {
        return Arrays.asList(new Object[][]{
                {new Provider(CustomLinkedListBasedStack::new, "based on custom implementation of linked list")},
                {new Provider(LinkedListBasedStack::new, "based on linked list")},
                {new Provider(ArrayBasedStack::new, "based on resizable array")}
        });
    }

    @Test
    public void test() {
        Stack<String> stack = builder.build();

        stack.push("5");
        assertSizeAndEmptyFlag(stack, 1);
        assertEquals(stack.pop(), "5");
        assertSizeAndEmptyFlag(stack, 0);

        stack.push("4");
        assertSizeAndEmptyFlag(stack, 1);
        stack.push("3");
        assertSizeAndEmptyFlag(stack, 2);
        assertEquals(stack.pop(), "3");
        assertSizeAndEmptyFlag(stack, 1);
        assertEquals(stack.pop(), "4");
        assertSizeAndEmptyFlag(stack, 0);

        stack.push("2");
        stack.push("6");
        stack.push("7");
        stack.push("8");
        stack.push("9");
        stack.push("10");
        assertSizeAndEmptyFlag(stack, 6);
        assertEquals(stack.pop(), "10");
        assertEquals(stack.pop(), "9");
        assertEquals(stack.pop(), "8");
        assertEquals(stack.pop(), "7");
        assertEquals(stack.pop(), "6");
        assertEquals(stack.pop(), "2");
        assertSizeAndEmptyFlag(stack, 0);

        stack.push("1");
        assertSizeAndEmptyFlag(stack, 1);
        assertEquals(stack.pop(), "1");
        assertSizeAndEmptyFlag(stack, 0);
    }

    @Test
    public void iterator() {
        Stack<String> stack = builder.build();

        stack.push("1");
        stack.push("2");
        stack.push("3");

        Iterator<String> i = stack.iterator();
        assertTrue(i.hasNext());
        assertEquals(i.next(), "3");
        assertTrue(i.hasNext());
        assertEquals(i.next(), "2");
        assertTrue(i.hasNext());
        assertEquals(i.next(), "1");
        assertFalse(i.hasNext());
    }

    private static void assertSizeAndEmptyFlag(final Stack stack, final int expectedSize) {
        assertEquals(stack.size(), expectedSize);
        assertEquals(stack.isEmpty(), expectedSize == 0);
    }

    /**
     * @author volodymyr.tsukur
     */
    private static final class Provider {

        private final Builder builder;

        private final String name;

        public Provider(final Builder builder, final String name) {
            this.builder = builder;
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    /**
     * @author volodymyr.tsukur
     */
    private interface Builder {

        Stack<String> build();

    }

}