class Solution {
    public int[] nodesBetweenCriticalPoints(ListNode head) {
        int[] ans = {-1, -1};

        if (head == null || head.next == null || head.next.next == null) {
            return ans;
        }

        ListNode prev = head;
        ListNode curr = head.next;

        int index = 1;
        int first = -1;
        int last = -1;
        int minDist = Integer.MAX_VALUE;

        while (curr.next != null) {
            // Critical point: local maximum or local minimum
            if ((curr.val > prev.val && curr.val > curr.next.val) ||
                (curr.val < prev.val && curr.val < curr.next.val)) {

                if (first == -1) {
                    first = index;
                } else {
                    minDist = Math.min(minDist, index - last);
                }

                last = index;
            }

            prev = curr;
            curr = curr.next;
            index++;
        }

        if (first == last) {
            return ans;
        }

        int maxDist = last - first;

        ans[0] = minDist;
        ans[1] = maxDist;

        return ans;
    }
}