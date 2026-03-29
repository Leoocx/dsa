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
    /*
Soma: (N1*D2 + N2*D1) / (D1*D2)
Subtração: (N1*D2 - N2*D1) / (D1*D2)
Multiplicação: (N1*N2) / (D1*D2)
Divisão: (N1/D1) / (N2/D2), ou seja (N1*D2)/(N2*D1) */

    public static int mdc(int a, int b) {
        return b == 0 ? a : mdc(b, a % b);
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int count = Integer.parseInt(reader.readLine());
        while (count>0) {
            String input = reader.readLine();
            String[] p = input.split(" ");

            int n1 = Integer.parseInt(p[0]);
            int d1 = Integer.parseInt(p[2]);

            String op = p[3];

            int n2 = Integer.parseInt(p[4]);
            int d2 = Integer.parseInt(p[6]);
            
            StringBuilder sb = new StringBuilder();
            int x = Integer.MAX_VALUE;
            int y = Integer.MAX_VALUE;
            switch (op) {
                case "/":
                    x = n1*d2;
                    y = d1*n2;
                    sb.append(x);
                    sb.append("/");
                    sb.append(y);
                    break;
                case "*":
                    x = n1*n2;
                    y = d1*d2;
                    sb.append(x);
                    sb.append("/");
                    sb.append(y);
                    break;
                case "+":
                    x = n1*d2 + n2*d1;
                    y = d1*d2;
                    sb.append(x);
                    sb.append("/");
                    sb.append(y);
                    break;
                case "-":
                    x = n1*d2 - n2*d1;
                    y = d1*d2;
                    sb.append(x);
                    sb.append("/");
                    sb.append(y);
                    break;    
                default:
                    break;
            }
            int mdc = mdc(x, y);
            x/=mdc;
            y/=mdc;

            if (y<0){
                x=(-1)*x;
                y=(-1)*y;
            }

            sb.append(" = ");
            sb.append(x);
            sb.append("/");
            sb.append(y);
            System.out.println(sb.toString());
            count--;
        }
 
    }
 
}