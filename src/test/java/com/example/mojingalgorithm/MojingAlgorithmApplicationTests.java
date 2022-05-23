package com.example.mojingalgorithm;

import org.junit.jupiter.api.Test;
import org.openjdk.jol.info.ClassLayout;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MojingAlgorithmApplicationTests {

    static class A {
        int a;
        int[] b;
        int[] c = new int[3];
    }

    @Test
    void contextLoads() {
        A a = new A();
        String b = "abc";
        System.out.println(ClassLayout.parseInstance(b).toPrintable());
    }

}
