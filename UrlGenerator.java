
public class UrlGenerator {
    private String CompanyName;
    private static final char[] ENGLISH_VOWELS = {'a', 'e', 'i', 'o', 'u'};

    // Constructor
    public UrlGenerator(String companyName) {
        CompanyName = companyName;
    }

    // Set CompanyName
    public void setCompanyName(String companyName) {
        CompanyName = companyName;
    }

    // Compute the URL
    private String compute() {
        String protocol = buildProtocol();
        String hostname = buildHostName();
        String path = buildPath();
    
        return protocol + hostname + path;
    }

    // Get the generated URL
    public String getGeneratedURL() {
        return compute();
    }

    // Build URL protocol with validation
    private String buildProtocol() {
        // Split the company name into parts
        String[] companyNameParts = CompanyName.split(" ");
        // Get the first part of the company name
        String firstCompanyNamePart = companyNameParts[0].toLowerCase();

        // Check if the first part of the company name is "meta", and return the appropriate protocol
        if (firstCompanyNamePart.equals("meta")) {
            return "coap://";
        } else {
            return "ftp://";
        }
    }

    private String replaceCompanyNameParts() {
        // Replace the words directly
        String replacedCompanyName = CompanyName.replace("Limited Liability Company", "LLC")
                                               .replace("Limited", "Ltd")
                                               .replace("Incorporated", "Inc");
    
        // Split the company name into parts and join them with underscores
        String[] companyNameParts = replacedCompanyName.split(" ");
        String companyFirstName = companyNameParts[0];
        for (int i = 1; i < companyNameParts.length; i++) {
            companyFirstName += "_" + companyNameParts[i];
        }
    
        return companyFirstName + ".";
    }

    // Build URL hostname
    private String buildHostName() {
        // instantiate a StringBuilder object - it's more efficient than concatenating strings
        StringBuilder hostnameBuilder = new StringBuilder();

        hostnameBuilder.append(replaceCompanyNameParts());
    
        // Counts the vowels and consonants in the company name and appends .edu or .org depending on if the number of vowels is even or odd
        int consonants = countConsonants();
        
        // Uses modulus to determine if the number of vowels is even or odd
        if (consonants % 2 == 0) {
            hostnameBuilder.append("edu");
        } else {
            hostnameBuilder.append("org");
        }
    
        return hostnameBuilder.toString();
    }

    // Counts the consonants in the company name
    private int countConsonants() {
        int consonants = 0;
        String companyName = CompanyName.toLowerCase();
    
        // Iterates over the company name and counts the consonants, validating whether the character is a letter or not
        for (int i = 0; i < companyName.length(); i++) {
            char c = companyName.charAt(i);
    
            if (Character.isLetter(c) && !isVowel(c)) {
                consonants++;
            }
        }
    
        return consonants;
    }

    // Checks if the character is a vowel, using the constant ENGLISH_VOWELS
    private boolean isVowel(char c) {
        for (char vowel : ENGLISH_VOWELS) {
            if (c == vowel) {
                return true;
            }
        }
        return false;
    }

    // Counts the number of vowel pairs in the company name
    private int countVowelPairs() {
        int vowelPairs = 0;
        String companyName = CompanyName.toLowerCase();
        boolean previousCharWasVowel = false;
    
        // Iterate through the characters in the CompanyName, and if it is a letter, check if it is a vowel. If it is a vowel, check if the previous character was a vowel. If it was, increment the vowelPairs variable. If it wasn't, set previousCharWasVowel to true.
        for (int i = 0; i < companyName.length(); i++) {
            char c = companyName.charAt(i);
    
            if (isVowel(c)) {
                if (previousCharWasVowel) {
                    vowelPairs++;
                    previousCharWasVowel = false; // Reset to avoid counting triples, quadruples, etc. as multiple pairs
                } else {
                    previousCharWasVowel = true;
                }
            } else {
                previousCharWasVowel = false;
            }
        }
    
        return vowelPairs;
    }

    // Build URL path according to the number of vowel pairs in the company name
    private String buildPath() {
        int vowelPairs = countVowelPairs();
    
        if (vowelPairs == 0) {
            return "/bio";
        } else if (vowelPairs >= 1 && vowelPairs <= 3) {
            return "/FAQ";
        } else {
            return "/Glossary";
        }
    }
}

