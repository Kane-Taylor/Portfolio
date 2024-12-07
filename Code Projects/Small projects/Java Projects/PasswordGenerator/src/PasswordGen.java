import java.util.Random;

public class PasswordGen {
    //possible characters
    public static final String lowerCase = "qwertyuiopasdfghjklzxcvbnm";
    public static final String upperCase = "QWERTYUIOPASDFGHJKLZXCVBNM";
    public static final String symbols = "!,./;'[]()*&^%$£#<>?@:{}";
    public static final String numbers = "0123456789";

    private final Random random;
    public PasswordGen(){random = new Random();}
    public String generatedPassword(int length, boolean lowercaseUsed, boolean uppercaseUsed,boolean numberUsed,boolean symbolUsed)
    {
        StringBuilder passwordBuilder = new StringBuilder();
        String validCharacter = "";
        if(lowercaseUsed) validCharacter += lowerCase;
        if(uppercaseUsed) validCharacter += upperCase;
        if(symbolUsed) validCharacter += symbols;
        if(numberUsed) validCharacter += numbers;

        for(int i = 0; i < length; i++)
        {
            int randomIndex = random.nextInt(validCharacter.length());
            char randomChar = validCharacter.charAt(randomIndex);
            passwordBuilder.append(randomChar);


        }

        return passwordBuilder.toString();

    }



}

