package ua.nure.illiashenko_ilona.pzpi_19_7.cyclingteamhealth;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Base64Util {

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static  String decodeString(String encodedString) {
        byte[] decodedBytes = Base64.getDecoder().decode(encodedString);
        return new String(decodedBytes)
                .replace("%7B","{")
                .replace("%22", "\"")
                .replace("%3A", ":")
                .replace("%2C", ",")
                .replace("%20", " ")
                .replace("%7D", "}")
                .replace("%40", "@");
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String encodeString(String string){
        return Base64.getEncoder().encodeToString(string.getBytes(StandardCharsets.UTF_8));
    }
}
