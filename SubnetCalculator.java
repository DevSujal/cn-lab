import java.util.Scanner;

public class SubnetCalculator {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("=== Subnet Calculator Menu ===");
            System.out.println("1. Classful Addressing");
            System.out.println("2. Classless Addressing (CIDR)");
            System.out.println("3. Custom Subnetting");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    classfulAddressing(scanner);
                    break;
                case 2:
                    classlessAddressing(scanner);
                    break;
                case 3:
                    customSubnetting(scanner);
                    break;
                case 4:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 4);

        scanner.close();
    }

    private static void classfulAddressing(Scanner scanner) {
        System.out.print("Enter IP Address: ");
        String ip = scanner.next();

        try {
            String[] octets = ip.split("\\.");
            int firstOctet = Integer.parseInt(octets[0]);

            String subnetMask = "";
            if (firstOctet >= 0 && firstOctet <= 127) {
                subnetMask = "255.0.0.0";
            } else if (firstOctet >= 128 && firstOctet <= 191) {
                subnetMask = "255.255.0.0";
            } else if (firstOctet >= 192 && firstOctet <= 223) {
                subnetMask = "255.255.255.0";
            } else {
                System.out.println("Invalid IP class.");
                return;
            }

            calculateNetworkDetails(ip, subnetMask);
        } catch (Exception e) {
            System.out.println("Invalid IP Address.");
        }
    }

    private static void classlessAddressing(Scanner scanner) {
        System.out.print("Enter IP Address: ");
        String ip = scanner.next();
        System.out.print("Enter Prefix Length (e.g., 24 for /24): ");
        int prefixLength = scanner.nextInt();

        if (prefixLength < 0 || prefixLength > 32) {
            System.out.println("Invalid Prefix Length.");
            return;
        }

        String subnetMask = prefixToSubnetMask(prefixLength);
        calculateNetworkDetails(ip, subnetMask);
    }

    private static void customSubnetting(Scanner scanner) {
        System.out.print("Enter IP Address: ");
        String ip = scanner.next();
        System.out.print("Enter Number of Subnets: ");
        int numSubnets = scanner.nextInt();

        int bitsNeeded = (int) Math.ceil(Math.log(numSubnets) / Math.log(2));
        String[] octets = ip.split("\\.");
        int firstOctet = Integer.parseInt(octets[0]);
        int basePrefixLength = (firstOctet >= 0 && firstOctet <= 127) ? 8 :
                               (firstOctet >= 128 && firstOctet <= 191) ? 16 : 24;

        int newPrefixLength = basePrefixLength + bitsNeeded;
        String subnetMask = prefixToSubnetMask(newPrefixLength);
        System.out.println("Calculated Subnet Mask: " + subnetMask);
    }

    private static void calculateNetworkDetails(String ip, String subnetMask) {
        try {
            String[] ipOctets = ip.split("\\.");
            String[] maskOctets = subnetMask.split("\\.");

            int[] networkAddress = new int[4];
            int[] broadcastAddress = new int[4];

            for (int i = 0; i < 4; i++) {
                networkAddress[i] = Integer.parseInt(ipOctets[i]) & Integer.parseInt(maskOctets[i]);
                broadcastAddress[i] = networkAddress[i] | (~Integer.parseInt(maskOctets[i]) & 0xFF);
            }

            System.out.println("Network Address: " + join(networkAddress));
            System.out.println("Broadcast Address: " + join(broadcastAddress));
            networkAddress[networkAddress.length - 1] += 1;
            broadcastAddress[broadcastAddress.length - 1] -= 1;
            for(int i = broadcastAddress.length - 2; i >= 0; i--) {
                if(broadcastAddress[i] > 0) {
                    break;
                }
                broadcastAddress[i] = 255;
            }
            System.out.println("Host Range: " + join(networkAddress) + " - " + join(broadcastAddress));
        } catch (Exception e) {
            System.out.println("Error in calculating network details.");
        }
    }

    private static String prefixToSubnetMask(int prefixLength) {
        int mask = 0xffffffff << (32 - prefixLength);
        return String.format("%d.%d.%d.%d",
                (mask >> 24) & 0xff, (mask >> 16) & 0xff, (mask >> 8) & 0xff, mask & 0xff);
    }

    private static String join(int[] arr) {
        return arr[0] + "." + arr[1] + "." + arr[2] + "." + arr[3];
    }
}
