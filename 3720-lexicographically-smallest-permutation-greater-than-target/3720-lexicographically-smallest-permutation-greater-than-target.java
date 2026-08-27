class Solution {
    public String lexGreaterPermutation(String s, String target) {
        int n = s.length();
        int[] freq = new int[26];

        for (char c : s.toCharArray()) {
            freq[c - 'a']++;
        }

        // Try to make the answer greater at position i.
        for (int i = n - 1; i >= 0; i--) {

            // Rebuild remaining character counts for target[0..i-1]
            int[] count = freq.clone();

            boolean possible = true;

            for (int j = 0; j < i; j++) {
                int idx = target.charAt(j) - 'a';

                if (count[idx] == 0) {
                    possible = false;
                    break;
                }

                count[idx]--;
            }

            if (!possible) continue;

            int current = target.charAt(i) - 'a';

            // Pick the smallest character strictly greater
            // than target[i].
            int bigger = -1;

            for (int c = current + 1; c < 26; c++) {
                if (count[c] > 0) {
                    bigger = c;
                    break;
                }
            }

            if (bigger == -1) continue;

            StringBuilder ans = new StringBuilder();

            // Prefix equal to target
            ans.append(target, 0, i);

            // Make the first difference greater
            ans.append((char) ('a' + bigger));
            count[bigger]--;

            // Smallest possible suffix
            for (int c = 0; c < 26; c++) {
                while (count[c] > 0) {
                    ans.append((char) ('a' + c));
                    count[c]--;
                }
            }

            return ans.toString();
        }

        return "";
    }
}