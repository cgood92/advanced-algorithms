package ActivitySelection;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ActivitySelectionTest {

	@Test
	void test1() {
		String result = ActivitySelection.ACTIVITY_SELECTOR(
			new int[] {1, 5, 7, 10, 3, 10, 12, 17},
			new int[] {4, 6, 9, 11, 9, 12, 17, 18});
		String expected = "(1, 4) (5, 6) (7, 9) (10, 11) (12, 17) (17, 18)";
		assertEquals(expected, result);
	}

	@Test
	void test2() {
		String result = ActivitySelection.ACTIVITY_SELECTOR(
			new int[] {4, 5, 1, 2 },
			new int[] {5, 6, 2, 3 });
		String expected = "(4, 5) (5, 6)";
		assertEquals(expected, result);
	}

	@Test
	void test3() {
		String result = ActivitySelection.ACTIVITY_SELECTOR(
			new int[] {4, 5 },
			new int[] {5, 6 });
		String expected = "(4, 5) (5, 6)";
		assertEquals(expected, result);
	}


	@Test
	void test4() {
		String result = ActivitySelection.ACTIVITY_SELECTOR(
			new int[] {4, 5, 6, 7 },
			new int[] {5, 6, 7, 8 });
		String expected = "(4, 5) (5, 6) (6, 7) (7, 8)";
		assertEquals(expected, result);
	}

	@Test
	void test5() {
		String result = ActivitySelection.ACTIVITY_SELECTOR(
			new int[] {},
			new int[] {});
		String expected = "";
		assertEquals(expected, result);
	}

	@Test
	void test6() {
		String result = ActivitySelection.ACTIVITY_SELECTOR(
			new int[] {2, 10, 5, 7 },
			new int[] {4, 13, 6, 9 });
		String expected = "(2, 4) (5, 6) (7, 9) (10, 13)";
		assertEquals(expected, result);
	}


	@Test
	void test7() {
		String result = ActivitySelection.ACTIVITY_SELECTOR(
			new int[] {1, 10, 5, 2, 7, 3 },
			new int[] {2, 13, 6, 3, 8, 4 });

		String expected = "(1, 2) (2, 3) (3, 4) (5, 6) (7, 8) (10, 13)";
		assertEquals(expected, result);
	}
}