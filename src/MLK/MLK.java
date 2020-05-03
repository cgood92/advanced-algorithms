package MLK;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.stream.Stream;

public class MLK {
	public static void main(String args[]) {
		System.out.println(authorshipSimilarity());
		System.out.println(lcsAuthorshipSimilarity());
	}

	/*
	AUTHORSHIP_SIMILARITY(masterTerms, termsInFile2, termsInFile3)
	ft = array(array(masterTerms), array(termsInFile2), array(termsInFile3))
	ftc = GET_TERM_COUNTS(ft)
	tcf = TERM_FREQUENCY(ftc)
	at = array(masterTerms, termsInFile2, termsInFile3)
	idf = GET_INVERSE_DOCUMENT_FREQUENCY(at, ftc)

	tdifs = array(GET_TDIFS(at, tcf, idf))

	cf1 = COSINE(tdifs[0], tdifs[1])
	cf2 = COSINE(tdifs[0], tdifs[2])

	IF cf1 == cf2 return 0
	ELSE IF cf1 > cf2 return 1
	ELSE return -1
	 */

	public static int authorshipSimilarity() {
		String[] files = getFiles();
		String[][] fileTerms = Arrays.stream(files).map(file ->
			file.replaceAll("[\\W&&[^\\s]]", "").toLowerCase().split("\\W+")
		).toArray(String[][]::new);

		HashMap<String, Integer>[] fileTermCounts = Arrays
			.stream(fileTerms)
			.map(terms -> getTermCounts(terms))
			.toArray(HashMap[]::new);
		HashMap<String, Double>[] termCountFrequencies = Arrays
			.stream(fileTermCounts)
			.map(termCounts -> termFrequencyCalculation(termCounts))
			.toArray(HashMap[]::new);

		HashSet<String> allTerms = new HashSet<>(Arrays.asList(
			Arrays.stream(fileTerms).reduce( new String[0], (a, b) -> joinArrays(a, b))
		));

		HashMap<String, Double> inverseDocumentFrequencies = getInverseDocumentFrequencies(allTerms, fileTermCounts);


		Double[][] TFIDFs = getTFIDFs(allTerms, termCountFrequencies, inverseDocumentFrequencies);

		double cosFile1 = cosineSimilarity(TFIDFs[0], TFIDFs[1]);
		double cosFile2 = cosineSimilarity(TFIDFs[0], TFIDFs[2]);

		if (cosFile1 == cosFile2) return 0;
		else if (cosFile1 > cosFile2) return 1;
		else return -1;
	}

	public static String[] getFiles() {
		return new String[] {
				readFile("/Users/clintgoodman/development/advanced-algorithms/src/MLK/ihaveadream.txt"),
				readFile("/Users/clintgoodman/development/advanced-algorithms/src/MLK/mountaintop.txt"),
				readFile("/Users/clintgoodman/development/advanced-algorithms/src/MLK/othello.txt")
		};
	}

	public static String readFile(String filename) {
		String contents = "";

		try {
			File file = new File(filename);
			Scanner reader = new Scanner(file);

			while (reader.hasNextLine()) {
				String data = reader.nextLine();
				contents += data + " ";
			}

			reader.close();
		} catch (Exception e) {
			System.out.println("Error");
		}

		return contents;
	}

	public static String[] joinArrays(String[] firstArray, String[] secondArray) {
		return Stream.concat(Arrays.stream(firstArray), Arrays.stream(secondArray))
			.toArray(String[]::new);
	}

	/*
	GET_TDIFS(at, tcf, idf)
	r = array(array())
	FOR m = 0...tcf.length
	  FOR t = 0...at.length
	    IF t in TCF
	      r[m][t] = m[t] * idf[t]
	    ELSE r[m][t] = 0
	 */

	public static Double[][] getTFIDFs(HashSet<String> allTerms, HashMap<String, Double>[] termCountFrequencies, HashMap<String, Double> inverseDocumentFrequencies) {
		return Arrays.stream(termCountFrequencies).map(termCountFrequencyMap ->
			allTerms
				.stream()
				.map(term -> {
					if (termCountFrequencyMap.containsKey(term)) {
						return termCountFrequencyMap.get(term) * inverseDocumentFrequencies.get(term);
					}
					else return 0.0;
				})
				.toArray(Double[]::new)
		).toArray(Double[][]::new);
	}

	/*
	GET_INVERSE_DOCUMENT_FREQUENCY(at, ftc)
	r = []
	FOR t = 0...at.length
	  c = 0
	  FOR f = 0...ftc.length
	    IF t in ftc[f]
	      c = c + 1
	  f = 1 + log(at.length / c)
	  idf[t] = f
	return r
	 */

	public static HashMap<String, Double> getInverseDocumentFrequencies(HashSet<String> allTerms, HashMap<String, Integer>[] fileTermCounts) {
		HashMap<String, Double> inverseDocumentFrequencies = new HashMap<String, Double>();

		allTerms
			.stream()
			.forEach(term -> {
				Double occurences = ((Long) Arrays
					.stream(fileTermCounts)
					.filter(file -> file.containsKey(term))
					.count()
				).doubleValue();

				Double frequency = 1 + Math.log(allTerms.size() / occurences);

				inverseDocumentFrequencies.put(term, frequency);
			});

		return inverseDocumentFrequencies;
	}

	/*
	GET_TERM_COUNTS(ft)
	r = []
	FOR i = 0...ft.length
	  r[i] = 0
	FOR t = 0...ft.length
	  r[t] = r[t] + 1
	return r
	 */

	public static HashMap<String, Integer> getTermCounts(String[] terms) {
		HashMap<String, Integer> frequencyMap = new HashMap<String, Integer>();
		Arrays.stream(terms).forEach(term -> {
			if(frequencyMap.containsKey(term)) {
				frequencyMap.put(term, frequencyMap.get(term) + 1);
			} else {
				frequencyMap.put(term, 1);
			}
		});

		return frequencyMap;
	}

	/*
	TERM_FREQUENCY(ftc)
	r = []
	s = SUM(ftc)
	FOR se = 0...ftc.length
	  r[se] = ftc[se] / s
	return r
	 */

	public static HashMap<String, Double> termFrequencyCalculation(HashMap<String, Integer> termCounts) {
		HashMap frequencyTermMap = new HashMap<>();
		int sum = (int) Arrays.stream(termCounts.values().toArray()).reduce( 0,(a, b) -> (int) a + (int) b);

		termCounts.entrySet().stream().forEach(set -> {
			double termFrequency = (double) set.getValue() / (double) sum;
			frequencyTermMap.put((set.getKey()), termFrequency);
		});

		return frequencyTermMap;
	}

	/*
	COSINE(tdifs1, tdifs2)
	dp = 0
	m1 = 0
	m2 = 0
	cs = 0
	FOR i = 0...tdifs1.length
	  dp = dp + tdifs1[i] * tdifs2[i]
	  m1 = tdifs1[i] ^ 2
	  m2 = tdifs2[i] ^ 2
	m1 = SQUARE_ROOT(m1)
	m2 = SQUARE_ROOT(m2)
	return dp / (m1 * m2)
	 */

	/* Copied from: https://github.com/AdnanOquaish/Cosine-similarity-Tf-Idf-/blob/master/CosineSimilarity.java */
	public static double cosineSimilarity(Double[] docVector1, Double[] docVector2)
	{
		double dotProduct = 0.0;
		double magnitude1 = 0.0;
		double magnitude2 = 0.0;
		double cosineSimilarity = 0.0;

		for (int i = 0; i < docVector1.length; i++) //docVector1 and docVector2 must be of same length
		{
			dotProduct += docVector1[i] * docVector2[i]; //a.b
			magnitude1 += Math.pow(docVector1[i], 2); //(a^2)
			magnitude2 += Math.pow(docVector2[i], 2);//(b^2)
		}

		magnitude1 = Math.sqrt(magnitude1); //sqrt(a^2)
		magnitude2 = Math.sqrt(magnitude2); //sqrt(b^2)

		if (magnitude1 != 0.0 | magnitude2 != 0.0) {
			cosineSimilarity = dotProduct / (magnitude1 * magnitude2);
		} else {
			return 0.0;
		}

		return cosineSimilarity;
	}

	public static int lcsAuthorshipSimilarity(){
		String[] files = getFiles();

		char[] m = files[0].toCharArray();
		char[] s1 = files[1].toCharArray();
		char[] s2 = files[2].toCharArray();

		int lm = m.length;
		int l1 = s1.length;
		int l2 = s2.length;

		int t1 = lcs (m, s1, lm, l1);
		int t2 = lcs (m, s2, lm, l2);

		if (t1 == t2) return 0;
		else if (t1 > t2) return 1;
		else return -1;

	}

	/* Copied from: https://www.geeksforgeeks.org/longest-common-subsequence-dp-4/ */
	/* Returns length of LCS for X[0..m-1], Y[0..n-1] */
	public static int lcs( char[] X, char[] Y, int m, int n )
	{
		int L[][] = new int[m+1][n+1];

    /* Following steps build L[m+1][n+1] in bottom up fashion. Note
         that L[i][j] contains length of LCS of X[0..i-1] and Y[0..j-1] */
		for (int i=0; i<=m; i++)
		{
			for (int j=0; j<=n; j++)
			{
				if (i == 0 || j == 0)
					L[i][j] = 0;
				else if (X[i-1] == Y[j-1])
					L[i][j] = L[i-1][j-1] + 1;
				else
					L[i][j] = max(L[i-1][j], L[i][j-1]);
			}
		}
		return L[m][n];
	}

	/* Utility function to get max of 2 integers */
	public static int max(int a, int b)
	{
		return (a > b)? a : b;
	}
}
