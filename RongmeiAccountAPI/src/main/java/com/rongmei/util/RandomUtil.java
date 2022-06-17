package com.rongmei.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class RandomUtil {
    public static final String ALLCHAR = "0123456789";

    public static String generateNonceStr() {
        return new Date().getTime() + "";
    }

    public static String generateCode(int num) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < num; i++) {
            stringBuilder.append(ALLCHAR.charAt((int) Math.floor(Math.random() * ALLCHAR.length())));
        }
        return new String(stringBuilder);
    }

    public static List<Long> generateRandomElement(List<Long> inputs, int count) {
        List<Long> sources = new ArrayList<>(inputs);
        List<Long> targets = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            int randomIndex = new Random().nextInt(sources.size());
            targets.add(sources.get(randomIndex));
            sources.remove(randomIndex);
        }
        return targets;
    }
}
