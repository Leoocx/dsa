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

        while (n>0) {
            int l = Integer.parseInt(reader.readLine());
            String[] input = reader.readLine().split(" ");
            int[] v = new int[l];

            for(int i=0;i<l;i++){
                v[i]=Integer.parseInt(input[i]);
            }
            int swaps=0;
            for (int i=0;i<l;i++){
                for(int j=i+1;j<l;j++){
                    int actually=v[i];
                    if (actually>v[j]){
                        int temp = actually;
                        v[i]=v[j];
                        v[j]=temp;
                        swaps++;
                    }
                }
            } System.out.println("Optimal train swapping takes "+swaps+" swaps.");
            
            n--;
        }
        
 
    }
 
}