package com.example.mojingalgorithm;

import com.example.mojingalgorithm.mapper.LabelCountMapper;
import org.junit.jupiter.api.Test;
import org.openjdk.jol.info.ClassLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MojingAlgorithmApplicationTests {

    static class A {
        int a;
        int[] b;
        int[] c = new int[3];
    }

    @Autowired
    LabelCountMapper labelCountMapper;

    @Test
    void contextLoads() {
        A a = new A();
        String b = "abc";
        System.out.println(ClassLayout.parseInstance(b).toPrintable());
    }

    @Test
    void testUpdateLabel(){
//        labelCountMapper.updateLabel("print（图案）", "print");
    }

}
