import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class HashingUtil {
    public static void main(String[] args) {
        System.out.println("bcrypt: " + new BCryptPasswordEncoder().encode("secret"));
        System.out.println("bcrypt: " + new BCryptPasswordEncoder().encode("erik"));
        System.out.println("argon2: " + Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8().encode("secret"));
    }
}
