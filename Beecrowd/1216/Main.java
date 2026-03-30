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

        Double result = 0.0;
        int count = 0;
        while (true) {
            String s = reader.readLine();
            if (s==null || s.equals("")){
                break;
            }
            Double k = Double.parseDouble(reader.readLine());
            result+=k;
            count++;
        } System.out.println(Math.round((result/count)*10.0)/10.0);

 
    }
 
}