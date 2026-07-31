import java.util.Arrays;

class Solution {
    public int minimumPushes(String word) {
        int[] freq = new int[26];

        for (char c : word.toCharArray()) {
            freq[c - 'a']++;
        }

        Arrays.sort(freq);

        int ans = 0;

        for (int i = 25; i >= 0; i--) {
            if (freq[i] == 0) break;

            int position = 25 - i;
            ans += freq[i] * (position / 8 + 1);
        }

        return ans;
    }
}