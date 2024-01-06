import java.util.ArrayList;
import java.util.Arrays;
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
                int numUrls = 0;
                boolean validInput = false;
            
                // check whether the input is a valid integer
                do {
                    System.out.println("Enter the number of URLs to generate:");
                    if (scanner.hasNextInt()) {
                        numUrls = scanner.nextInt();
                        validInput = true;
                    } else {
                        System.out.println("Error: Please enter a valid numberß.");
                        scanner.next(); // Consume the invalid input
                    }
                } while (!validInput);
            
                scanner.nextLine(); // Consume newline left-over
            
                // Create string array to store company names
                List<String> companyNames = new ArrayList<>();
                for (int i = 0; i < numUrls; i++) {
                    System.out.println("Enter company name number " + (i + 1) + ":");
                    String companyName = scanner.nextLine();
                    companyNames.add(companyName);
                }
            
                // Generate URLs for each company name, return result
                for (String companyName : companyNames) {
                    UrlGenerator urlGenerator = new UrlGenerator(companyName);
                    String url = urlGenerator.getGeneratedURL();
                    System.out.println("Generated URL for " + companyName + ": " + url);
                }
                break;

                case 2:
                int numUrlsToValidate = 0;
                boolean validNumber = false;
            
                // Check whether the input is a valid integer
                do {
                    System.out.println("Enter the number of URLs to validate:");
                    if (scanner.hasNextInt()) {
                        numUrlsToValidate = scanner.nextInt();
                        validNumber = true;
                    } else {
                        System.out.println("Error: Please enter a valid number.");
                        scanner.next(); // Consume the invalid input
                    }
                } while (!validNumber);
            
                scanner.nextLine(); // Consume newline left-over
            
                // Create string array to store URLs
                List<String> urlsToValidate = new ArrayList<>();
                for (int i = 0; i < numUrlsToValidate; i++) {
                    System.out.println("Enter URL number " + (i + 1) + " to validate:");
                    String url = scanner.nextLine();
                    urlsToValidate.add(url);
                }

                // Validate URLs, return result
                List<Boolean> validationResultsList = validateUrls(urlsToValidate);
                boolean[] validationResults = new boolean[validationResultsList.size()];
                for (int i = 0; i < validationResultsList.size(); i++) {
                    validationResults[i] = validationResultsList.get(i);
                }
                System.out.println(Arrays.toString(validationResults));
                break;

                // Exit the program
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
            // Transform the URL to lowercase to make it easier to check for the hostname
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
            boolean validCharacters = url.matches("[a-zA-Z0-9-/:.]*");
    
            boolean isValid = containsGoogle && validHostname && validLength && validCharacters;
            results.add(isValid);
        }
    
        return results;
    }
}