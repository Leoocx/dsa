import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(reader.readLine());

        while (t > 0) {
            int n = Integer.parseInt(reader.readLine());
            String[] names = reader.readLine().split(" ");
            String[] freq = reader.readLine().split(" ");

            StringBuilder result = new StringBuilder();

            for (int i = 0; i < n; i++) {
                int p = 0;
                int a = 0;

                String word = freq[i];

                for (char c : word.toCharArray()) {
                    if (c == 'P') p++;
                    else if (c == 'A') a++;
                }

                if ((double) p / (p + a) < 0.75) {
                    if (result.length() > 0) result.append(" ");
                    result.append(names[i]);
                }
            }

            System.out.println(result.toString());
            t--;
        }
    }
}