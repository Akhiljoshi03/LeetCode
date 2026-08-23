class Solution {
    public boolean sumGame(String num) {
        int n = num.length();
        int half = n / 2;

        int leftSum = 0;
        int rightSum = 0;
        int leftQ = 0;
        int rightQ = 0;

        for (int i = 0; i < half; i++) {
            if (num.charAt(i) == '?') {
                leftQ++;
            } else {
                leftSum += num.charAt(i) - '0';
            }
        }

        for (int i = half; i < n; i++) {
            if (num.charAt(i) == '?') {
                rightQ++;
            } else {
                rightSum += num.charAt(i) - '0';
            }
        }

        // Difference in number of ? on both sides
        int qDiff = leftQ - rightQ;

        // Difference in known digit sums
        int sumDiff = leftSum - rightSum;

        /*
         * If the number of ? is odd, Alice can always
         * force the sums to be different.
         */
        if ((leftQ + rightQ) % 2 == 1) {
            return true;
        }

        /*
         * Bob wins only when the initial difference can
         * be perfectly balanced by the ? digits.
         */
        return Math.abs(sumDiff * 2 + qDiff * 9) != 0;
    }
}