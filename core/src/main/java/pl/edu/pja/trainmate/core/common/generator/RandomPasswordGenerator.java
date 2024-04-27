package pl.edu.pja.trainmate.core.common.generator;


import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;
import org.springframework.stereotype.Service;

@Service
public class RandomPasswordGenerator {

    public String generate(int length) {
        var digits = new CharacterRule(EnglishCharacterData.Digit);
        var generator = new PasswordGenerator();
        return generator.generatePassword(length, digits);
    }

}
