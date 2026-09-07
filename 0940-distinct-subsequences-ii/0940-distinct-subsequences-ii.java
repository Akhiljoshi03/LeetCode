class Solution {
    public int distinctSubseqII(String s) {
        int MOD = 1_000_000_007;
        
       
        int[] dp = new int[26];
        int total = 0;
        
        for (char c : s.toCharArray()) {
            int index = c - 'a';
            int previousCount = dp[index];
            
            
            dp[index] = (total + 1) % MOD;
            
       
            total = ((total - previousCount) % MOD + MOD) % MOD; 
            total = (total + dp[index]) % MOD;
        }
        
        return total;
    }
}