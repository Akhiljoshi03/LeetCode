class Solution {
    public String[] findWords(String[] words) {
        HashMap<Character, Integer> map = new HashMap<>();

        String row1 = "qwertyuiop";
        String row2 = "asdfghjkl";
        String row3 = "zxcvbnm";

        for (char c : row1.toCharArray()) map.put(c, 1);
        for (char c : row2.toCharArray()) map.put(c, 2);
        for (char c : row3.toCharArray()) map.put(c, 3);

        List<String> ans = new ArrayList<>();

        for (String word : words) {
            String s = word.toLowerCase();
            int row = map.get(s.charAt(0));
            boolean valid = true;

            for (int i = 1; i < s.length(); i++) {
                if (map.get(s.charAt(i)) != row) {
                    valid = false;
                    break;
                }
            }

            if (valid) ans.add(word);
        }

        return ans.toArray(new String[0]);
    }
}