import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
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
            String[] input1 = reader.readLine().split(" ");
            if (input1[0].equals("0") && input1[1].equals("0")){
                return;
            }            
            String[] input2 = reader.readLine().split(" ");
            HashMap<String, Integer> map = new HashMap<>();

            for(String s: input2){
                if (map.containsKey(s)){
                    map.computeIfPresent(s, (l, v) -> v + 1);
                }else{
                    map.put(s, 0);
                }
            }
            
            int c = 0;
            for (Map.Entry<String, Integer> entry : map.entrySet()) {
                if (entry.getValue()>0){
                    c++;
                }
            }
            System.out.println(c);
        }
 
    }
 
}