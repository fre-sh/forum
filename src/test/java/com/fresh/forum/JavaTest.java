package com.fresh.forum;

import java.util.Optional;

/**
 * @author guowenyu
 * @date 2020/5/28
 */
public class JavaTest {

    public static void main(String[] args) {
        Optional<String> optional = Optional.empty();
        String x = optional.orElse(null);
        System.out.println(x);
        x = optional.orElseGet(() -> null);
        System.out.println(x);
    }

}
