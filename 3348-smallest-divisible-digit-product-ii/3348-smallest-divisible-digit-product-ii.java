class Solution {
    // index 0->prime2, 1->prime3, 2->prime5, 3->prime7
    static final int[][] DIGIT_FACTORS = new int[10][4];
    static {
        DIGIT_FACTORS[2] = new int[]{1,0,0,0};
        DIGIT_FACTORS[3] = new int[]{0,1,0,0};
        DIGIT_FACTORS[4] = new int[]{2,0,0,0};
        DIGIT_FACTORS[5] = new int[]{0,0,1,0};
        DIGIT_FACTORS[6] = new int[]{1,1,0,0};
        DIGIT_FACTORS[7] = new int[]{0,0,0,1};
        DIGIT_FACTORS[8] = new int[]{3,0,0,0};
        DIGIT_FACTORS[9] = new int[]{0,2,0,0};
    }
    static final int[] PRIMES = {2,3,5,7};

    public String smallestNumber(String num, long t) {
        int[] primeCount = getPrimeCount(t);
        if (primeCount == null) return "-1"; // t has a prime factor other than 2,3,5,7 -> impossible

        int[] factorCount = getFactorCount(primeCount);
        int n = num.length();
        if (sumValues(factorCount) > n) {
            // Needs more digits than num has -> smallest answer is a fresh (n+1)-length-ish number
            return construct(factorCount);
        }

        int[] primeCountPrefix = getPrimeCountFromString(num);
        int firstZeroIndex = num.indexOf('0');
        if (firstZeroIndex == -1) {
            firstZeroIndex = n;
            if (isSubset(primeCount, primeCountPrefix)) {
                return num; // num itself already works
            }
        }

        for (int i = n - 1; i >= 0; i--) {
            int d = num.charAt(i) - '0';
            primeCountPrefix = subtract(primeCountPrefix, DIGIT_FACTORS[d]);
            int spaceAfterThisDigit = n - 1 - i;
            if (i > firstZeroIndex) continue; // can't keep original suffix, it contains a 0
            for (int biggerDigit = d + 1; biggerDigit < 10; biggerDigit++) {
                int[] remaining = subtract(subtract(primeCount, primeCountPrefix), DIGIT_FACTORS[biggerDigit]);
                int[] factorsAfterReplacement = getFactorCount(remaining);
                int s = sumValues(factorsAfterReplacement);
                if (s <= spaceAfterThisDigit) {
                    int fillOnes = spaceAfterThisDigit - s;
                    StringBuilder sb = new StringBuilder();
                    sb.append(num, 0, i);
                    sb.append((char) ('0' + biggerDigit));
                    for (int k = 0; k < fillOnes; k++) sb.append('1');
                    sb.append(construct(factorsAfterReplacement));
                    return sb.toString();
                }
            }
        }

        // No same-length answer works -> extend by one digit
        int[] factorsAfterExtension = getFactorCount(primeCount);
        int ones = n + 1 - sumValues(factorsAfterExtension);
        StringBuilder sb = new StringBuilder();
        for (int k = 0; k < ones; k++) sb.append('1');
        sb.append(construct(factorsAfterExtension));
        return sb.toString();
    }

    private int[] getPrimeCount(long t) {
        int[] count = new int[4];
        for (int p = 0; p < 4; p++) {
            int prime = PRIMES[p];
            while (t % prime == 0) {
                t /= prime;
                count[p]++;
            }
        }
        return t == 1 ? count : null;
    }

    private int[] getPrimeCountFromString(String num) {
        int[] count = new int[4];
        for (int i = 0; i < num.length(); i++) {
            int[] f = DIGIT_FACTORS[num.charAt(i) - '0'];
            for (int p = 0; p < 4; p++) count[p] += f[p];
        }
        return count;
    }

    // count indexed by prime (2,3,5,7) -> returns minimal digit multiset (indices 2..9) achieving it
    private int[] getFactorCount(int[] count) {
        int[] res = new int[10];
        int count8 = count[0] / 3;
        int remaining2 = count[0] % 3;
        int count9 = count[1] / 2;
        int count3 = count[1] % 2;
        int count4 = remaining2 / 2;
        int count2 = remaining2 % 2;
        int count6 = 0;
        if (count2 == 1 && count3 == 1) {
            count2 = 0; count3 = 0; count6 = 1;
        }
        if (count3 == 1 && count4 == 1) {
            count2 = 1; count6 = 1; count3 = 0; count4 = 0;
        }
        res[2] = count2;
        res[3] = count3;
        res[4] = count4;
        res[5] = count[2];
        res[6] = count6;
        res[7] = count[3];
        res[8] = count8;
        res[9] = count9;
        return res;
    }

    private String construct(int[] factors) {
        StringBuilder sb = new StringBuilder();
        for (int digit = 2; digit < 10; digit++)
            for (int k = 0; k < factors[digit]; k++) sb.append((char) ('0' + digit));
        return sb.toString();
    }

    private boolean isSubset(int[] a, int[] b) {
        for (int i = 0; i < 4; i++) if (b[i] < a[i]) return false;
        return true;
    }

    private int[] subtract(int[] a, int[] b) {
        int[] res = new int[a.length];
        for (int i = 0; i < a.length; i++) res[i] = Math.max(0, a[i] - b[i]);
        return res;
    }

    private int sumValues(int[] a) {
        int s = 0;
        for (int v : a) s += v;
        return s;
    }
}