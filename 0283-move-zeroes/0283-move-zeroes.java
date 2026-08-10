class Solution {
    public void moveZeroes(int[] nums) {
        int index = 0;

        // Put all non-zero elements at the front
        for (int num : nums) {
            if (num != 0) {
                nums[index++] = num;
            }
        }

        // Fill the remaining positions with zeroes
        while (index < nums.length) {
            nums[index++] = 0;
        }
    }
}