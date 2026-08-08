class Solution {
    public int[] validSequence(String word1, String word2) {
        int n = word1.length();
        int m = word2.length();

        // suf[i] = earliest position in word1 where
        // word2[i...] can be matched exactly.
        int[] suf = new int[m];
        java.util.Arrays.fill(suf, -1);

        int j = m - 1;

        for (int i = n - 1; i >= 0 && j >= 0; i--) {
            if (word1.charAt(i) == word2.charAt(j)) {
                suf[j] = i;
                j--;
            }
        }

        int[] ans = new int[m];

        int p = 0;
        boolean mismatchUsed = false;

        for (int i = 0; i < n && p < m; i++) {

            // Exact match
            if (word1.charAt(i) == word2.charAt(p)) {
                ans[p] = i;
                p++;
            }

            // Use the one allowed mismatch
            else if (!mismatchUsed) {

                // If this is the last character, mismatch is valid.
                if (p == m - 1) {
                    ans[p] = i;
                    p++;
                    mismatchUsed = true;
                }

                // Otherwise, remaining characters must be
                // exactly matchable after position i.
                else if (suf[p + 1] != -1 && suf[p + 1] > i) {
                    ans[p] = i;
                    p++;
                    mismatchUsed = true;
                }
            }
        }

        if (p == m) {
            return ans;
        }

        return new int[0];
    }
}