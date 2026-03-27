

class Solution {

    public ListNode deleteDuplicates(ListNode head) {
        ListNode actually=head;
        
        while (actually!=null && actually.next!=null) {
            if (actually.next.val==actually.val){
                actually.next=actually.next.next;
            }   else{
                actually=actually.next;
            }
        }
        return head;
    }


}