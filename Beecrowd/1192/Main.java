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
        int n = Integer.parseInt(reader.readLine());
        String m="abcdefghijklmnopqrstuvwxyz";
        String M=m.toUpperCase();

        while (n>0) {
            String[] input = reader.readLine().split("");
            int x = Integer.parseInt(input[0]);
            int y = Integer.parseInt(input[2]);
            if (x==y) {
                System.out.println(x*y);

            }else{
                String w = input[1];
                if(M.contains(w)){
                    System.out.println(y-x);
                    
                }else if (m.contains(w)){
                    System.out.println(x+y);
                }
            }
            n--;
        }
        

    }

}