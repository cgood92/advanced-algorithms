package ActivitySelection;

import java.util.ArrayList;
import java.util.List;

/*
ACTIVITY_SELECTION(s, f)
SORT_PAIRS_NON_DECREASING_BY_FINISH(s, f)

r[0] = [s[0], f[0]]
INSERT_INTO_ARRAY(r, FIND_INDEX_TO_INSERT(s, [s[1], f[1]]), [s[1], f[1]])

m = 0
for i = 2 to s.length - 1
    if (s[i] >= f[m])
        y = FIND_INDEX_TO_INSERT(s, [s[i], f[i]], m)
        if y
            x = FIND_INDEX_TO_INSERT(s, [s[i+1], f[i+1]], 0)
            if x
                INSERT_INTO_ARRAY(r, y, [s[i], f[i]])
                INSERT_INTO_ARRAY(r, x, [s[i+1], f[i+1]])
                m++
    i = i + 2

return r
 */

public class ActivitySelection
{

    public static String ACTIVITY_SELECTOR(int[] starts, int[] finishes) {
        if ((starts.length != finishes.length) || (starts.length % 2 != 0)) {
            throw new IllegalArgumentException("Array inputs not valid");
        }

        if (starts.length == 0) {
            return "";
        }

        List<int []> results = new ArrayList<>();

        results.add(new int[]{ starts[0], finishes[0] });

        int[] second = new int[]{ starts[1], finishes[1] };
        results.add(findIndexToInsert(results, second, 0), second);

        int lastInsertedIndex = 0;

        for (int i = 2; i <= starts.length - 1; i += 2) {
            if (starts[i] >= finishes[lastInsertedIndex]) {
                int[] pair1 = new int[]{ starts[i], finishes[i] };
                int insertionPoint1 = findIndexToInsert(results, pair1, lastInsertedIndex);

                if (insertionPoint1 >= 0) {
                    int[] pair2 = new int[]{ starts[i + 1], finishes[i + 1] };
                    int insertionPoint2 = findIndexToInsert(results, pair2, 0);

                    if (insertionPoint2 >= 0) {
                        int offset = pair1[1] < pair2[1] ? 1 : 0;

                        results.add(insertionPoint1, pair1);
                        results.add(insertionPoint2 + offset, pair2);

                        lastInsertedIndex = i;
                    }
                }
            }
        }

        return prettyString(results);
    }

    public static int findIndexToInsert(List<int[]> results, int[] pair, int startingPoint) {
        for (int i = startingPoint; i < results.size(); i++) {
            int[] targetPair = results.get(i);

            if (pair[0] >= targetPair[1]) {
                boolean isLast = i == results.size() - 1;
                if (isLast) {
                    return i + 1;
                }

                int[] nextTargetPair = results.get(i + 1);
                if (pair[1] <= nextTargetPair[0]) {
                    return i + 1;
                }
            }
        }

        return -1;
    }


    public static String prettyString(List<int[]> list) {
        String result = "";

        for (int[] pair : list) {
            if (result.length() != 0) {
                result += " ";
            }
            result += "(" + pair[0] + ", " + pair[1] + ")";
        }

        return result;
    }
}
