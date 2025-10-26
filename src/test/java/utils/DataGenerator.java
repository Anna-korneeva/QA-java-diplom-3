package utils;  // ← Добавь эту строку

import java.util.Random;

public class DataGenerator {
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final Random random = new Random();

    public static String randomAlphanumeric(int minLength, int maxLength) {
        int length = random.nextInt(maxLength - minLength + 1) + minLength;
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(index));
        }
        return sb.toString();
    }

    public static String generateRandomEmail() {
        return randomAlphanumeric(6, 10) + "@yandex.ru";
    }

    public static String generateRandomName() {
        return randomAlphanumeric(4, 8);
    }

    public static String generateRandomPassword(int minLength, int maxLength) {
        return randomAlphanumeric(minLength, maxLength);
    }
}