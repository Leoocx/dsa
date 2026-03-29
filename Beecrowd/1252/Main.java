import java.io.*;
import java.util.*;

public class Main {

    static class Value {
        int value;
        int mod;

        Value(int value, int m) {
            this.value = value;
            this.mod = value % m;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            String[] input = reader.readLine().split(" ");

            int n = Integer.parseInt(input[0]);
            int m = Integer.parseInt(input[1]);

            if (n == 0 && m == 0) {
                System.out.println("0 0");
                break;
            }

            List<Value> list = new ArrayList<>();

            for (int i = 0; i < n; i++) {
                int num = Integer.parseInt(reader.readLine());
                list.add(new Value(num, m));
            }

            Collections.sort(list, (a, b) -> {

                // 1. ordenar pelo módulo
                if (a.mod != b.mod) {
                    return Integer.compare(a.mod, b.mod);
                }

                // 2. ímpar antes de par
                boolean aOdd = a.value % 2 != 0;
                boolean bOdd = b.value % 2 != 0;

                if (aOdd && !bOdd) return -1;
                if (!aOdd && bOdd) return 1;

                // 3. ambos ímpares → maior primeiro
                if (aOdd) {
                    return Integer.compare(b.value, a.value);
                }

                // 4. ambos pares → menor primeiro
                return Integer.compare(a.value, b.value);
            });

            System.out.println(n + " " + m);
            for (Value v : list) {
                System.out.println(v.value);
            }
        }
    }
}