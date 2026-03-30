import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.SortedSet;
import java.util.TreeSet;
 
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
        HashMap<Integer, Integer> map = new HashMap<>();

        while (n>0) {
            Integer k = Integer.parseInt(reader.readLine());
            if (map.containsKey(k)){
                map.computeIfPresent(k, (l, v) -> v + 1);
            }else{
                map.put(k, 1);
            }
            n--;
        }
        SortedSet<Integer> keys = new TreeSet<>(map.keySet());
        for (Integer k: keys){
            System.out.println(k + " aparece " + map.get(k)+" vez(es)");
            
        }
        /*for (Map.Entry<Integer, Integer> entrada : map.entrySet()) {
                System.out.println(entrada.getKey() + " aparece " + entrada.getValue()+" vez(es)");
            }*/

 
    }
 
}