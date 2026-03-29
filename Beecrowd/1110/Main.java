import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;
 
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
            StringBuilder remainingCard = new StringBuilder();
            StringBuilder discardedCards = new StringBuilder();

            int n = Integer.parseInt(reader.readLine());
            if (n==0) {
                return;
            }
            int[] stack = new int[n];        
            
            for (int i=1;i!=n+1;i++){
                stack[i-1]=i;
            }

            
            for (int j=0;j<n;j++){
                if (j==n-1){
                    remainingCard.append(stack[j]);
                    break;
                }
                if (discardedCards.length() > 0) {
                    discardedCards.append(", ");
                }
                    discardedCards.append(stack[j]);
                
                stack[j]=-1;
                for(int k=j+1;k<n-1;k++){
                    int aux = stack[k];
                    stack[k]=stack[k+1];
                    stack[k+1]=aux;
                } 
            }

            System.out.println("Discarded cards: "+discardedCards.toString());
            System.out.println("Remaining card: "+remainingCard.toString());
        }
 
    }
 
}