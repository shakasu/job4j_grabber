package ru.job4j;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class HelloTest {

    @Test
    public void hi() {
        Hello hello = new Hello();
        assertThat(hello.hi(), is("Hello"));
    }
}
