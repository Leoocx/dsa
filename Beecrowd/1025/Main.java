import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {

    public static int BinarySearch(int[] arr, int target) {
        int left = 0, right = arr.length - 1;
        int answer = -1;

        while (left <= right) {
            int mid = (left + right) / 2;

            if (arr[mid] == target) {
                answer = mid;
                right = mid - 1; 
            } else if (arr[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return answer;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        int c = 1;
        String line;

        while ((line = reader.readLine()) != null) {
            if (line.trim().isEmpty()) continue;

            String[] input = line.split(" ");
            if (input.length < 2) continue;

            int n = Integer.parseInt(input[0]);
            int q = Integer.parseInt(input[1]);

            if (n == 0 && q == 0) return;

            int[] nValues = new int[n];

            for (int i = 0; i < n; i++) {
                nValues[i] = Integer.parseInt(reader.readLine());
            }

            Arrays.sort(nValues);

            System.out.println("CASE# " + c + ":");

            for (int i = 0; i < q; i++) {
                int query = Integer.parseInt(reader.readLine());

                int y = BinarySearch(nValues, query);

                if (y != -1) {
                    System.out.println(query + " found at " + (y + 1));
                } else {
                    System.out.println(query + " not found");
                }
            }

            c++;
        }
    }
}