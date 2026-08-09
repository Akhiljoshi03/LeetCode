class Solution {
    int[][] dp;
    int[] suffix;
    int n;

    public int stoneGameII(int[] piles) {
        n = piles.length;
        dp = new int[n][n + 1];
        suffix = new int[n + 1];

        // Suffix sum
        for (int i = n - 1; i >= 0; i--) {
            suffix[i] = suffix[i + 1] + piles[i];
        }

        return solve(0, 1, piles);
    }

    private int solve(int i, int m, int[] piles) {
        if (i >= n) {
            return 0;
        }

        if (dp[i][m] != 0) {
            return dp[i][m];
        }

        // Can take all remaining piles
        if (2 * m >= n - i) {
            return dp[i][m] = suffix[i];
        }

        int best = 0;

        for (int x = 1; x <= 2 * m; x++) {
            // Current player gets all remaining stones
            // minus what the opponent can get.
            int opponent = solve(i + x, Math.max(m, x), piles);
            best = Math.max(best, suffix[i] - opponent);
        }

        return dp[i][m] = best;
    }
}