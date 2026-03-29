import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            int n = Integer.parseInt(reader.readLine());
            if (n == 0) break;

            while (true) {
                String line = reader.readLine();
                if (line.equals("0")) {
                    System.out.println();
                    break;
                }

                String[] input = line.split(" ");
                int[] target = new int[n];
                for (int i = 0; i < n; i++) {
                    target[i] = Integer.parseInt(input[i]);
                }

                Stack<Integer> stack = new Stack<>();
                int current = 1; 
                int i = 0;       

                while (current <= n) {
                    stack.push(current);

                    while (!stack.isEmpty() && stack.peek() == target[i]) {
                        stack.pop();
                        i++;
                    }

                    current++;
                }

                if (stack.isEmpty()) System.out.println("Yes");
                else System.out.println("No");
            }
        }
    }
}