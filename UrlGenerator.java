import java.util.HashMap;

public class UrlGenerator {
    private String CompanyName;
    private HashMap<String, String> Replacements;
    private static final char[] ENGLISH_VOWELS = {'a', 'e', 'i', 'o', 'u'};

    // Constructor
    public UrlGenerator(String companyName) {
        CompanyName = companyName;
        Replacements = new HashMap<>();
        Replacements.put("Incorporated", "Inc");
        Replacements.put("Limited", "Ltd");
        Replacements.put("Limited Liability", "LLC");
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

    // Build URL hostname
    private String buildHostName() {
        // instantiate a StringBuilder object - it's more efficient than concatenating strings
        StringBuilder hostnameBuilder = new StringBuilder();
        // split the company name into parts
        String[] companyNameParts = CompanyName.split(" ");
    
        // It starts at index = 1 because the first part of the string is the company name - this loop iterates over the rest of the parts and replaces it with the values in the hashmap
        for (int i = 1; i < companyNameParts.length; i++) {
            String companyNamePart = companyNameParts[i];
    
            for (String key : Replacements.keySet()) {
                if (companyNamePart.contains(key)) {
                    companyNamePart = companyNamePart.replace(key, Replacements.get(key));
                }
            }
            
            // Replaces spaces with underscores
            hostnameBuilder.append(companyNamePart.replace(" ", "_"));
    
            // Appends a period if it is the last part of the company name
            if (i < companyNameParts.length - 1) {
                hostnameBuilder.append(".");
            }
        }
    
        // Counts the vowels and consonants in the company name and appends .edu or .org depending on if the number of vowels is even or odd
        int[] counts = countVowelsAndConsonants();
        int vowels = counts[0];
        int consonants = counts[1];
        
        // Uses modulus to determine if the number of vowels is even or odd
        if (vowels % 2 == 0) {
            hostnameBuilder.append("edu");
        } else if (consonants % 2 == 1) {
            hostnameBuilder.append("org");
        } else {
            hostnameBuilder.append("org");
        }
    
        return hostnameBuilder.toString();
    }

    // Counts the vowels and consonants in the company name
    private int[] countVowelsAndConsonants() {
        int vowels = 0;
        int consonants = 0;
        String companyName = CompanyName.toLowerCase();

        // Iterates over the company name and counts the vowels and consonants, validating whether the character is a letter or not
        for (int i = 0; i < companyName.length(); i++) {
            char c = companyName.charAt(i);

            if (Character.isLetter(c)) {
                if (isVowel(c)) {
                    vowels++;
                } else {
                    consonants++;
                }
            }
        }

        return new int[]{vowels, consonants};
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

