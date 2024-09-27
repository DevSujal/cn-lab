import java.util.Scanner;

public class Bytestuffing {

    public static String senderByteStuffing(String data, char flag, char esc) {
        StringBuilder stuffedData = new StringBuilder();
        stuffedData.append(flag); 

        for (int i = 0; i < data.length(); i++) {
            if (data.charAt(i) == flag || data.charAt(i) == esc) {
                stuffedData.append(flag);
            }
            stuffedData.append(data.charAt(i));
        }

        stuffedData.append(flag);
        return stuffedData.toString();
    }

    public static String receiverByteUnstuffing(String stuffedData, char flag, char esc) {
        StringBuilder originalData = new StringBuilder();
        boolean escapeNext = false;

        for (int i = 1; i < stuffedData.length() - 1; i++) {
            if (escapeNext) {
                originalData.append(stuffedData.charAt(i));
                escapeNext = false;
            } else if (stuffedData.charAt(i) == flag) {
                escapeNext = true; 
            } else {
                originalData.append(stuffedData.charAt(i));
            }
        }
        return originalData.toString();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter data for Byte Stuffing: ");
        String data = sc.nextLine();

        char flag = 'R'; 
        char esc = 'E'; 

        String stuffedData = senderByteStuffing(data, flag, esc);
        System.out.println("Stuffed Data: " + stuffedData);

        String receivedData = receiverByteUnstuffing(stuffedData, flag, esc);
        System.out.println("Unstuffed Data (Received): " + receivedData);
        sc.close();
    }
}
