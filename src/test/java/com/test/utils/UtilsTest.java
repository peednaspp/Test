package com.test.utils;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UtilsTest {

    @Test
    public void testReverseCase1() {
        assertThat(StringUtils.reverseWords("reversed"))
                .isEqualTo("desrever");
    }

    @Test
    public void testReverseCase2() {
        assertThat(StringUtils.reverseWords("rever35sed"))
                .isEqualTo("des53rever");
    }

    @Test
    public void testReverseCase3() {
        assertThat(StringUtils.reverseWords("Stri_ng;-%"))
                .isEqualTo("irtS_gn;-%");
    }

    @Test
    public void testReverseCase4() {
        assertThat(StringUtils.reverseWords(";-%Stri_ng"))
                .isEqualTo(";-%irtS_gn");
    }

    @Test
    public void testReverseCase5() {
        assertThat(StringUtils.reverseWords(";-%   2b$#e "))
                .isEqualTo(";-%   b2$#e ");
    }

    @Test
    public void testReverseCase6() {
        assertThat(StringUtils.reverseWords("Stri_ng;-%   2b$#e reversed"))
                .isEqualTo("irtS_gn;-%   b2$#e desrever");
    }

}
