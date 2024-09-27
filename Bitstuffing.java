import java.util.Scanner;

public class Bitstuffing {

  public static String senderBitStuffing(String data) {
    StringBuilder stuffedData = new StringBuilder();
    int count = 0;

    for (int i = 0; i < data.length(); i++) {
      if (data.charAt(i) == '1') {
        count++;
        stuffedData.append('1');
      } else {
        count = 0;
        stuffedData.append('0');
      }
      if (count == 5) {
        stuffedData.append('0');
        count = 0;
      }
    }
    return stuffedData.toString();
  }

  public static String receiverBitUnstuffing(String stuffedData) {
    StringBuilder originalData = new StringBuilder();
    int count = 0;

    for (int i = 0; i < stuffedData.length(); i++) {
      if (stuffedData.charAt(i) == '1') {
        count++;
        originalData.append('1');
      } else {
        originalData.append('0');
        count = 0;
      }
      if (count == 5 && i + 1 < stuffedData.length() && stuffedData.charAt(i + 1) == '0') {
        i++;
        count = 0;
      }
    }
    return originalData.toString();
  }

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    System.out.println("Enter binary data for Bit Stuffing: ");
    String data = sc.nextLine();

    String stuffedData = senderBitStuffing(data);
    System.out.println("Stuffed Data: " + stuffedData);

    String receivedData = receiverBitUnstuffing(stuffedData);
    System.out.println("Unstuffed Data (Received): " + receivedData);
    sc.close();
  }
}
