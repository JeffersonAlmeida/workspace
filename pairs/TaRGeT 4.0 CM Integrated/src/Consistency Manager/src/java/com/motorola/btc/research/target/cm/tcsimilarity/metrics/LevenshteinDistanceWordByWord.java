package com.motorola.btc.research.target.cm.tcsimilarity.metrics;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * This classs holds the methods to compute a modified Levenshtein distance.
 * <p>
 * Levenshtein distance (LD) is a measure of the similarity between two <code>String</code>
 * objects, which we will refer to as the source string (s) and the target string (t). The distance
 * is the number of deletions, insertions, or substitutions required to transform s into t. For
 * example,
 * <p>
 * If s is "the test" and t is "the test", then LD(s,t) = 0, because no transformations are needed
 * and the strings are already identical.<br/> If s is "the test" and t is "a test", then LD(s,t) =
 * 1, because one substitution (change "the" to "a") is sufficient to transform s into t. <br/>
 * <p>
 * The greater the Levenshtein distance, the more different the strings are. The measure is named
 * after the Russian scientist Vladimir Levenshtein, who devised the algorithm in 1965. If you can't
 * spell or pronounce Levenshtein, the metric is also sometimes called edit distance. TODO: The
 * spelling checker currently doesn't use this class. The code also needs to be optimized.
 */
public final class LevenshteinDistanceWordByWord
{

    /** A reusable int matrix, used to compute the Levenshtein distance. */
    private int[][] matrix;

    /** The maximum used number of rows in the matrix. */
    private int maxN = -1;

    /** The maximum used number of columns in the matrix. */
    private int maxM = -1;

    public LevenshteinDistanceWordByWord()
    {
        getMatrix(100, 100);
    }

    public double levenshteinDistanceWordByWord(String s, String t)
    {
        return (levenshteinDistanceWordByWord(toStringList(s), toStringList(t), toStringList(s
                .toLowerCase()), toStringList(t.toLowerCase()), false)) / 1000.0;
    }

    public double modifiedLevenshteinDistanceWordByWord(String s, String t)
    {
        return (levenshteinDistanceWordByWord(toStringList(s), toStringList(t), toStringList(s
                .toLowerCase()), toStringList(t.toLowerCase()), true)) / 1000.0;
    }

    private List<String> toStringList(String s)
    {
        List<String> result = new ArrayList<String>();
        if (s != null)
        {
            StringTokenizer tokens = new StringTokenizer(s);
            while (tokens.hasMoreTokens())
            {
                result.add(removeLastCharacterIfSpecial(tokens.nextToken()));
            }
        }
        return result;
    }

    private String removeLastCharacterIfSpecial(String s)
    {
        String result = "";
        if (!s.equals("") && s != null)
        {
            int size = (s.length() - 1);
            char lastChar = s.charAt(size);
            if (lastChar == '.' || lastChar == ',' || lastChar == ';' || lastChar == '?'
                    || lastChar == '!' || lastChar == ':' || lastChar == '(' || lastChar == ')'
                    || lastChar == '{' || lastChar == '}' || lastChar == '[' || lastChar == ']'
                    || lastChar == '<' || lastChar == '>' || lastChar == '*' || lastChar == '%'
                    || lastChar == '@' || lastChar == '#' || lastChar == '$' || lastChar == '&')
            {
                StringBuilder word = new StringBuilder();
                for (int i = 0; i <= (size - 1); i++)
                {
                    word.append(s.charAt(i));
                }
                result = word.toString();
            }
            else
            {
                result = s;
            }
        }
        return result;
    }

    public int levenshteinDistanceWordByWord(List<String> s, List<String> t,
            List<String> lowercaseS, List<String> lowercaseT, boolean useCaseDifference)
    {
        int d[][]; // matrix
        int n; // # of words in s
        int m; // # of words in t
        int i; // iterates through s
        int j; // iterates through t
        String s_i; // ith word of s
        String t_j; // jth word of t
        String u_s_i; // ith word of s (lowercased)
        String u_t_j; // jth word of t (lowercased)
        int cost; // cost
        boolean caseDifference = false;
        int returnValue = 0;
        n = s.size();
        m = t.size();
        if (n == 0)
            return m;
        if (m == 0)
            return n;
        d = getMatrix(n + 1, m + 1);
        for (i = 0; i <= n; i++)
            d[i][0] = i;
        for (j = 0; j <= m; j++)
            d[0][j] = j;
        boolean not_finished = true;
        for (i = 1; not_finished == true && i <= n; i++)
        {
            s_i = s.get(i - 1);
            u_s_i = lowercaseS.get(i - 1);
            for (j = 1; not_finished == true && j <= m; j++)
            {
                t_j = t.get(j - 1);
                u_t_j = lowercaseT.get(j - 1);
                if (u_s_i.equals(u_t_j))
                {
                    cost = 0;
                    if ((!s_i.equals(t_j)) && (i == j))
                    {
                        caseDifference = true;
                    }
                }
                else
                    cost = 1;
                d[i][j] = minimum(d[i - 1][j] + 1, d[i][j - 1] + 1, d[i - 1][j - 1] + cost);
            }
        }
//        printD(d, s.size(), t.size());
        returnValue = d[n][m] * 1000;
        if (caseDifference && useCaseDifference)
            returnValue = returnValue + 500;
        return (returnValue);
    }

    private static int minimum(int a, int b, int c)
    {
        int mi;
        mi = a;
        if (b < mi)
            mi = b;
        if (c < mi)
            mi = c;
        return mi;

    }

    private int[][] getMatrix(int n, int m)
    {
        boolean rebuild = false;
        if (n > this.maxN)
        {
            this.maxN = n + 10;
            rebuild = true;
        }
        if (m > this.maxM)
        {
            this.maxM = m + 10;
            rebuild = true;
        }
        if (rebuild == true)
            this.matrix = new int[maxN][maxM];
        return (this.matrix);
    }

}
