package Functions;

public class spamChecking {
    private final static double similarityScore = 0.75;

    public static boolean isSpam (PostQueue queue, Post post){
        return isSimilarContent(queue, post.getContent());
    }
    public static boolean isSimilarContent(PostQueue queue, String comment){
        for(Post p:queue.getAllPosts()){
            String compare = p.getContent();
            double score = StringSimilarity.similarity(compare, comment);
            if(score > similarityScore)
                return true;
        }
        return false;
    }
}

class StringSimilarity {
    /**
     * Calculates the similarity (a number within 0 and 1) between two strings.
     */
    public static double similarity(String s1, String s2) {
        String longer = s1.replaceAll("[\\t\\n\\r]+"," ");
        String shorter = s2.replaceAll("[\\t\\n\\r]+"," ");
        if (s1.length() < s2.length()) { // longer should always have greater length
            longer = s2.replaceAll("[\\t\\n\\r]+"," ");
            shorter = s1.replaceAll("[\\t\\n\\r]+"," ");
        }
        int longerLength = longer.length();
        if (longerLength == 0) { return 1.0; /* both strings are zero length */ }
        /* // If you have StringUtils, you can use it to calculate the edit distance:
        return (longerLength - StringUtils.getLevenshteinDistance(longer, shorter)) /
                                                             (double) longerLength; */
        return (longerLength - editDistance(longer, shorter)) / (double) longerLength;
    }
 
    // Example implementation of the Levenshtein Edit Distance
    // See http://rosettacode.org/wiki/Levenshtein_distance#Java
    public static int editDistance(String s1, String s2) {
        s1 = s1.toLowerCase();
        s2 = s2.toLowerCase();
 
        int[] costs = new int[s2.length() + 1];
        for (int i = 0; i <= s1.length(); i++) {
            int lastValue = i;
            for (int j = 0; j <= s2.length(); j++) {
                if (i == 0)
                    costs[j] = j;
                else {
                    if (j > 0) {
                        int newValue = costs[j - 1];
                        if (s1.charAt(i - 1) != s2.charAt(j - 1))
                            newValue = Math.min(Math.min(newValue, lastValue),
                                    costs[j]) + 1;
                        costs[j - 1] = lastValue;
                        lastValue = newValue;
                    }
                }
            }
            if (i > 0)
                costs[s2.length()] = lastValue;
        }
        return Math.abs(costs[s2.length()]);
    }
}