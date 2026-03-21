class Matrix{
   
    public static void sum(int[][] A, int[][] B){

        if (A.length != B.length || A[0].length != B[0].length) {
    throw new IllegalArgumentException(
        "The matrices must have the same dimensions for addition."
        );
    }
        
        int p = A.length;
        int m = A[0].length;
        int[][] ans = new int[p][m];
 
        int j=0;  
        for(int i=0;i<A.length;i++){     
            for (int k=0;k<m;k++){
                ans[i][k] = A[i][k]+B[j][k];
                System.out.printf("%4d ", ans[i][k]);
            }j++; System.out.println();
        }
    }
    
    public static void multiply(int[][] A, int[][] B){
        
        if (A[0].length != B.length) {
            throw new IllegalArgumentException(
                "Multiplication impossible: Columns of A != Rows of B");
        }

        int p = A.length;
        int m = B[0].length;
        int[][] ans = new int[p][m];
 
        
        for(int i=0;i<A.length;i++){    
            for (int k=0;k<m;k++){         
                for(int j=0;j<B.length;j++){
                    ans[i][k]+=A[i][j]*B[j][k];
                }
            }
        }

         for (int[] row : ans) {
            for (int aij : row) {
                System.out.print(aij + " ");
            }
            System.out.println();
        }
    }

}