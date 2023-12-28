package me.fedox.skcord.utils;

public class ColorManager {
    public static int convertHexToDecimal(String hexCode) {
        if (hexCode.startsWith("#")) {
            hexCode = hexCode.substring(1);
        }

        if (hexCode.length() == 3) {
            StringBuilder extendedHex = new StringBuilder(6);
            for (char c : hexCode.toCharArray()) {
                extendedHex.append(c).append(c);
            }
            hexCode = extendedHex.toString();
        }

        return Integer.parseInt(hexCode, 16);
    }
}
