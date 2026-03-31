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
 
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            int[] aux = new int[2];
            int n = Integer.parseInt(reader.readLine());
            if (n==0) break;
            String[] line = reader.readLine().split(" ");

            for(String c: line){
                if (c.equals("0")) aux[0]+=1;
                else aux[1]+=1;
            }
            System.out.println("Mary won "+ aux[0] +" times and John won "+ aux[1] + " times");
        }

        
 
    }
 
}