import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * IMPORTANT: 
 *      O nome da classe deve ser "Main" para que a sua solução execute
 *      Class name must be "Main" for your solution to execute
 *      El nombre de la clase debe ser "Main" para que su solución ejecutar
 */
public class Main {

    public static boolean temDigitosRepetidos(int num) {
        boolean[] visited = new boolean[10];

        while (num > 0) {
            int d = num % 10;

            if (visited[d]) return true; 

            visited[d] = true;
            num /= 10;
        }

        return false; // todos diferentes
    }
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] input = line.split(" ");

            int n = Integer.parseInt(input[0]);
            int m = Integer.parseInt(input[1]);
            
            int c = 0;
            for(int i=n;i<=m;i++){
                if (!temDigitosRepetidos(i)) {
                    c++;
                }
            }            
            
            System.out.println(c);
        }
    }
 
}