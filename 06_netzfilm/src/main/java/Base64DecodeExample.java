import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Base64DecodeExample {
    public static void main(String[] args) {
        String base64Input = "dXNlcjpiZmI5MjRkZi0wNTQ0LTQ5NTMtYjA4Mi00Mzc0ZWM1OTc0NzY=";
        byte[] decodedBytes = Base64.getDecoder().decode(base64Input);
        String decodedString = new String(decodedBytes, StandardCharsets.UTF_8);
        System.out.println(decodedString);
    }
}
