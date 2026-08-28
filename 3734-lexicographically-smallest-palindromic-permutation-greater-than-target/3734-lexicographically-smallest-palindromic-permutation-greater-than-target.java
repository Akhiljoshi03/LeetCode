import java.util.*;

class Solution {
    public String lexPalindromicPermutation(String s, String target) {
        int n = s.length();
        int[] count = new int[26];
        for (char c : s.toCharArray()) {
            count[c - 'a']++;
        }

        // Check if s can form a palindrome
        int oddCount = 0;
        char midChar = 0;
        for (int i = 0; i < 26; i++) {
            if (count[i] % 2 != 0) {
                oddCount++;
                midChar = (char) ('a' + i);
            }
        }
        if (oddCount > 1) {
            return "";
        }

        // Half frequencies for building the left half
        int[] halfCount = new int[26];
        for (int i = 0; i < 26; i++) {
            halfCount[i] = count[i] / 2;
        }

        int m = n / 2;

        // Try prefix lengths matching target's first half from m down to 0
        for (int i = m; i >= 0; i--) {
            // Check if target[0...i-1] can be formed using available halfCount
            int[] currentHalf = halfCount.clone();
            boolean validPrefix = true;
            for (int j = 0; j < i; j++) {
                char c = target.charAt(j);
                if (currentHalf[c - 'a'] <= 0) {
                    validPrefix = false;
                    break;
                }
                currentHalf[c - 'a']--;
            }

            if (!validPrefix) continue;

            // Determine starting character at position i
            int startChar = (i < m) ? (target.charAt(i) - 'a' + 1) : 0;

            for (int c = startChar; c < 26; c++) {
                if (i < m && currentHalf[c] <= 0) continue;

                int[] tempHalf = currentHalf.clone();
                if (i < m) {
                    tempHalf[c]--;
                }

                // Construct the full string with smallest remaining chars
                StringBuilder left = new StringBuilder();
                left.append(target, 0, i);
                if (i < m) {
                    left.append((char) ('a' + c));
                }

                for (int ch = 0; ch < 26; ch++) {
                    while (tempHalf[ch] > 0) {
                        left.append((char) ('a' + ch));
                        tempHalf[ch]--;
                    }
                }

                StringBuilder full = new StringBuilder(left);
                if (n % 2 != 0) {
                    full.append(midChar);
                }
                StringBuilder right = new StringBuilder(left).reverse();
                full.append(right);

                String candidate = full.toString();
                if (candidate.compareTo(target) > 0) {
                    return candidate;
                }
            }
        }

        return "";
    }
}