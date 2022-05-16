package com.example.mojingalgorithm.util;

import java.util.Random;

public class ClothesDistribution {

    final static int CLOTH_CATEGORIES_COUNT = 7;
    final static int CLOTH_LABELS_COUNT = 10;
    public final static String[] CLOTH_CATEGORIES = {"夹克", "风衣", "毛衣", "T恤", "紧身裤", "牛仔裤", "短裤"};
    public final static String[] CLOTH_LABELS = {"运动", "图案", "紧身", "束腰", "棉布",
                                                 "薄纱", "复古", "条纹", "连帽", "金属"};

    public static int[] randomCategoryDistribution(int count) {
        int[] categories = new int[CLOTH_CATEGORIES_COUNT];
        return splitInto(count, categories);
    }

    public static int[] randomLabelDistribution(int count) {
        int[] labels = new int[CLOTH_LABELS_COUNT];
        Random random = new Random();
        for (int i = 0; i < CLOTH_LABELS_COUNT; i++) {
            labels[i] = random.nextInt(count / 2 - 1) + 1;
        }
        return labels;
    }

    public static float randomMetric(float i) {
        Random random = new Random();
        i += random.nextFloat()/50 - 0.5;
        return i;
    }

    private static int[] splitInto(int number, int[] numberOfSplits) {
        Random random = new Random();
        int n = number/(numberOfSplits.length);
        for (int i = 0; i < numberOfSplits.length; i++) {
            if (number > 1) {
                int j = n + random.nextInt(10) - 5;
                if (j < 0){
                    j = 0;
                    continue;
                }
                number -= j;
                numberOfSplits[i] = j;
            } else if (number == 1) {
                numberOfSplits[i + 1] = number;
                break;
            }
        }
        return numberOfSplits;
    }
}
