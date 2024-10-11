import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class SubnetCalculator {

    // Method to calculate subnet information
    public static void calculateSubnet(String ip, int subnetBits) {
        try {
            // Get the IP address as a byte array
            InetAddress inet = InetAddress.getByName(ip);
            byte[] ipBytes = inet.getAddress();

            // Calculate the subnet mask based on the number of bits
            int mask = (int) (0xFFFFFFFF << (32 - subnetBits));

            // Get network address by performing bitwise AND with subnet mask
            byte[] networkAddress = new byte[4];
            for (int i = 0; i < 4; i++) {
                networkAddress[i] = (byte) (ipBytes[i] & (mask >> (8 * (3 - i))));
            }

            // Get broadcast address by performing bitwise OR with subnet mask inverse
            byte[] broadcastAddress = new byte[4];
            for (int i = 0; i < 4; i++) {
                broadcastAddress[i] = (byte) (networkAddress[i] | ~((mask >> (8 * (3 - i))) & 0xFF));
            }

            // Print results
            System.out.println("IP Address: " + ip);
            System.out.println("Network Address: " + byteArrayToIP(networkAddress));
            System.out.println("Broadcast Address: " + byteArrayToIP(broadcastAddress));
            System.out.println("Subnet Mask: " + byteArrayToIP(intToByteArray(mask)));
            System.out.println("Number of Hosts: " + (int) (Math.pow(2, 32 - subnetBits) - 2));

            InetAddress firstHost = InetAddress.getByAddress(increment(networkAddress));
            InetAddress lastHost = InetAddress.getByAddress(decrement(broadcastAddress));
            System.out.println("Host Range: " + firstHost.getHostAddress() + " - " + lastHost.getHostAddress());

        } catch (UnknownHostException e) {
            System.out.println("Invalid IP address: " + e.getMessage());
        }
    }

    // Helper method to convert a byte array to IP string
    public static String byteArrayToIP(byte[] bytes) {
        StringBuilder ip = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            ip.append(bytes[i] & 0xFF);
            if (i < bytes.length - 1) {
                ip.append(".");
            }
        }
        return ip.toString();
    }

    // Helper method to convert an int subnet mask to a byte array
    public static byte[] intToByteArray(int value) {
        return new byte[] {
                (byte) ((value >> 24) & 0xFF),
                (byte) ((value >> 16) & 0xFF),
                (byte) ((value >> 8) & 0xFF),
                (byte) (value & 0xFF)
        };
    }

    // Method to increment the network address for the first host
    public static byte[] increment(byte[] ip) {
        for (int i = ip.length - 1; i >= 0; i--) {
            if ((ip[i] & 0xFF) < 255) {
                ip[i]++;
                break;
            }
        }
        return ip;
    }

    // Method to decrement the broadcast address for the last host
    public static byte[] decrement(byte[] ip) {
        for (int i = ip.length - 1; i >= 0; i--) {
            if ((ip[i] & 0xFF) > 0) {
                ip[i]--;
                break;
            }
        }
        return ip;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Input IP and subnet bits
        System.out.print("Enter the IP address: ");
        String ip = scanner.nextLine();

        System.out.print("Enter the subnet bits (e.g., 24 for /24): ");
        int subnetBits = scanner.nextInt();

        // Calculate and display subnet information
        calculateSubnet(ip, subnetBits);

        scanner.close();
    }
}
