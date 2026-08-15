class Solution {
    public int longestSubsequence(int[] nums) {
        int n = nums.length;
        int xor = 0;
        boolean hasNonZero = false;

        for (int num : nums) {
            xor ^= num;

            if (num != 0) {
                hasNonZero = true;
            }
        }

        // Entire array has non-zero XOR
        if (xor != 0) {
            return n;
        }

        // XOR is 0, but we have a non-zero element.
        // Remove one element -> answer is n - 1.
        if (hasNonZero) {
            return n - 1;
        }

        // All elements are 0
        return 0;
    }
}