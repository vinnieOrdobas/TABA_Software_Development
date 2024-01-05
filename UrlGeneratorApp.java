import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UrlGeneratorApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Please select an option:");
            System.out.println("1: Generate URLs");
            System.out.println("2: Validate URLs");
            System.out.println("3: Exit");

            int option = scanner.nextInt();

            switch (option) {
                case 1:
                    System.out.println("Enter the number of URLs to generate:");
                    int numUrls = scanner.nextInt();
                    scanner.nextLine(); // Consume newline left-over

                    List<String> companyNames = new ArrayList<>();
                    for (int i = 0; i < numUrls; i++) {
                        System.out.println("Enter company name number " + (i + 1) + ":");
                        String companyName = scanner.nextLine();
                        companyNames.add(companyName);
                    }

                    for (String companyName : companyNames) {
                        UrlGenerator urlGenerator = new UrlGenerator(companyName);
                        String url = urlGenerator.getGeneratedURL();
                        System.out.println("Generated URL for " + companyName + ": " + url);
                    }
                    break;

                case 2:
                    System.out.println("Enter the number of URLs to validate:");
                    int numUrlsToValidate = scanner.nextInt();
                    scanner.nextLine(); // Consume newline left-over

                    List<String> urlsToValidate = new ArrayList<>();
                    for (int i = 0; i < numUrlsToValidate; i++) {
                        System.out.println("Enter URL number " + (i + 1) + " to validate:");
                        String url = scanner.nextLine();
                        urlsToValidate.add(url);
                    }

                    List<Boolean> validationResults = validateUrls(urlsToValidate);
                    for (int i = 0; i < validationResults.size(); i++) {
                        System.out.println("URL number " + (i + 1) + " is " + (validationResults.get(i) ? "valid" : "invalid"));
                    }
                    break;

                case 3:
                    System.out.println("Exiting...");
                    scanner.close();
                    System.exit(0);
            }
        }
    }

    public static List<Boolean> validateUrls(List<String> urls) {
        List<Boolean> results = new ArrayList<>();
    
        for (String url : urls) {
            url = url.toLowerCase();
    
            boolean containsGoogle = url.contains("google");
    
            // The regular expression ".*\\.(com|ie).*" checks if the URL contains either ".com" or ".ie".
            // The ".*" at the beginning and end of the expression means that any characters (or no characters) can precede or follow ".com" or ".ie".
            // This is the best approach because it allows us to check for ".com" or ".ie" anywhere in the URL.
            boolean validHostname = url.matches(".*\\.(com|ie).*");
    
            boolean validLength = url.length() >= 6;
    
            // The regular expression "[a-z0-9-/.]*" checks if the URL only contains lowercase letters (a-z), digits (0-9), hyphens (-), forward slashes (/), and periods (.).
            // The "*" at the end of the expression means that any number of these characters (including zero) can appear in the URL.
            // This is the best approach because it allows us to check for only the allowed characters in the URL.
            boolean validCharacters = url.matches("[a-z0-9-/.]*");
    
            boolean isValid = containsGoogle && validHostname && validLength && validCharacters;
            results.add(isValid);
        }
    
        return results;
    }
}