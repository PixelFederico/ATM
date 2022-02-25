import org.apache.commons.lang3.RandomStringUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class RandomPassword {
    public static String generateCommonLangPassword() {
        String upperCaseLetters = RandomStringUtils.random(4, 65, 90, true, true);
        String numbers = RandomStringUtils.randomNumeric(4);
        String combinedChars = upperCaseLetters
                .concat(numbers);
        List<Character> pwdChars = combinedChars.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.toList());
        Collections.shuffle(pwdChars);
        String password = pwdChars.stream()
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
        return password;
    }
}
