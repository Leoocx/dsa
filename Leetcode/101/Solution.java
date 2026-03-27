
    
 
class Solution {
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode() {}
        TreeNode(int val) { this.val = val; }
        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }
    

    public boolean isSymmetric(TreeNode root) {
        if (root==null) return true;
        return aux(root.left, root.right);
    }
    public boolean aux(TreeNode n1, TreeNode n2){
        if (n1 == null && n2 == null) return true;
        if (n1 == null || n2 == null) return false;
        if (n1.val != n2.val) return false;
        
        return aux(n1.left, n2.right) && aux(n1.right, n2.left);
    }
}