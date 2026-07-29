import java.math.BigInteger;

class Solution {
    private static final BigInteger LIMIT = BigInteger.valueOf(1000001);

    public String smallestPalindrome(String s, int k) {
        int[] freq = new int[26];

        for (char c : s.toCharArray())
            freq[c - 'a']++;

        int[] half = new int[26];
        int halfLen = 0;
        char mid = 0;

        for (int i = 0; i < 26; i++) {
            half[i] = freq[i] / 2;
            halfLen += half[i];
            if ((freq[i] & 1) == 1)
                mid = (char) ('a' + i);
        }

        if (countWays(half).compareTo(BigInteger.valueOf(k)) < 0)
            return "";

        StringBuilder left = new StringBuilder();

        while (halfLen > 0) {
            for (int c = 0; c < 26; c++) {
                if (half[c] == 0)
                    continue;

                half[c]--;

                BigInteger ways = countWays(half);

                if (ways.compareTo(BigInteger.valueOf(k)) >= 0) {
                    left.append((char) ('a' + c));
                    halfLen--;
                    break;
                } else {
                    k -= ways.intValue();
                    half[c]++;
                }
            }
        }

        StringBuilder ans = new StringBuilder(left);

        if (mid != 0)
            ans.append(mid);

        ans.append(new StringBuilder(left).reverse());

        return ans.toString();
    }

    private BigInteger countWays(int[] cnt) {
        int total = 0;
        for (int x : cnt)
            total += x;

        BigInteger ans = BigInteger.ONE;
        int rem = total;

        for (int x : cnt) {
            if (x == 0)
                continue;

            ans = ans.multiply(comb(rem, x));

            if (ans.compareTo(LIMIT) > 0)
                return LIMIT;

            rem -= x;
        }

        return ans;
    }

    private BigInteger comb(int n, int r) {
        if (r > n - r)
            r = n - r;

        BigInteger res = BigInteger.ONE;

        for (int i = 1; i <= r; i++) {
            res = res.multiply(BigInteger.valueOf(n - r + i));
            res = res.divide(BigInteger.valueOf(i));

            if (res.compareTo(LIMIT) > 0)
                return LIMIT;
        }

        return res;
    }
}