import java.util.Arrays;

public class HammingCode {

    public static int[] parityCalculator(int p, int hamming[]) {
        int parity[] = new int[p];
        int i = 0;
        while ((1 << i) < hamming.length) {
            parity[i]  = hamming[(1 << i) - 1];
            i++;
        }

        System.out.println("parity bits are : " + Arrays.toString(parity));

        for (i = 1; i <= hamming.length; i++) {
            for (int j = 1; j <= parity.length; j++) {
                if (i != j && (((j) & i) > 0)) {
                    parity[j - 1] ^= hamming[i - 1];
                }
            }
        }
        return parity;
    }

    public static void errorCorrection(int parity[], int []hamming) {
        int error = 0;

        for (int i = 0; i < parity.length; i++) {
            error += (parity[i] << i);
        }

        error--;

        System.out.println("Error at position: " + (error + 1));
        hamming[error] = hamming[error] == 0 ? 1 : 0;
        System.out.println("Corrected hamming code: " + Arrays.toString(hamming));
    }
    public static void main(String[] args) {
        int hamming[] = {1, 1, 0, 1, 1, 0, 1};
        System.out.println("we have 7 bit hamming code as " + Arrays.toString(hamming));
        System.out.println("we have to detect and correct error using even parity");

        // Error detection
        // System.out.println(Arrays.toString(parityCalculator(3, hamming)));
        int count = 0;
        while ((1 << count) <= hamming.length) {
            count++;
        }
        int parity[] = parityCalculator(count, hamming);

        for (int i : parity) {
            if(i == 1) {
                System.out.println("Error detected");
                errorCorrection(parity, hamming);
                System.exit(0);
            }
        }


        System.out.println("No error detected");
    }
}