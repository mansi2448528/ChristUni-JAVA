import java.util.Scanner;

public class CreditCard{
     private String ccNumber;

     //initializing the ccnumber//
public CreditCard(String ccNumber)
{
    this.ccNumber=ccNumber;
}
public void validate() {
    // Check if the credit card number length is valid
    if (ccNumber.length() < 8 || ccNumber.length() > 9) {
        System.out.println("Invalid credit card number");
        return;
    }

    int[] digits = new int[ccNumber.length()];
        for (int i = 0; i < ccNumber.length(); i++) 
        {
            digits[i] = Character.getNumericValue(ccNumber.charAt(i));
        }
        
        // Step a: Remove the last digit of the ccNumber
        int lastDigit = digits[digits.length - 1];
        int[] remainingDigits = new int[digits.length - 1];
        System.arraycopy(digits, 0, remainingDigits, 0, digits.length - 1);

        // Step b: Reverse the remaining digits
        for (int i = 0; i < remainingDigits.length / 2; i++) {
            int temp = remainingDigits[i];
            remainingDigits[i] = remainingDigits[remainingDigits.length - 1 - i];
            remainingDigits[remainingDigits.length - 1 - i] = temp;
        }

        // Step c: Double the digits in odd-numbered positions and adjust for double digits
        for (int i = 0; i < remainingDigits.length; i++) {
            if (i % 2 == 0) { // odd-numbered positions (1st, 3rd, 5th, etc.)
                remainingDigits[i] *= 2;
                if (remainingDigits[i] > 9) {
                    remainingDigits[i] = remainingDigits[i] / 10 + remainingDigits[i] % 10;
                }
            }
        }

        // Step d: Add up all the digits
        int sum = 0;
        for (int digit : remainingDigits) {
            sum += digit;
        }

        // Step e: Subtract the last digit from 10
        int stepE = 10 - (sum % 10);

        // Step f: Compare step e result with the last digit
        switch (stepE) {
            case 10:
                stepE = 0;
            case 0:
                if (stepE == lastDigit) {
                    System.out.println("Valid credit card number");
                } else {
                    System.out.println("Invalid credit card number");
                }
                break;
            default:
                System.out.println("Invalid credit card number");
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Input the credit card number
        System.out.println("Enter the credit card number: ");
        String ccNumber = scanner.nextLine();

        // Create an object of CreditCard and validate the card
        CreditCard validator = new CreditCard(ccNumber);
        validator.validate();

        scanner.close();
    }
}
