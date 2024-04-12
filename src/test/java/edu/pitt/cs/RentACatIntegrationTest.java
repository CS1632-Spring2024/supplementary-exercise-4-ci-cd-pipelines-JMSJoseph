package edu.pitt.cs;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import static org.junit.Assert.*;

import org.mockito.Mockito;
import static org.mockito.Mockito.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Method;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RentACatIntegrationTest {

	/**
	 * The test fixture for this JUnit test. Test fixture: a fixed state of a set of
	 * objects used as a baseline for running tests. The test fixture is initialized
	 * using the @Before setUp method which runs before every test case. The test
	 * fixture is removed using the @After tearDown method which runs after each
	 * test case.
	 */

	RentACat r; // Object to test
	Cat c1; // First cat object
	Cat c2; // Second cat object
	Cat c3; // Third cat object

	ByteArrayOutputStream out; // Output stream for testing system output
	PrintStream stdout; // Print stream to hold the original stdout stream
	String newline = System.lineSeparator(); // Platform independent newline ("\n" or "\r\n") for use in assertEquals

	@Before
	public void setUp() throws Exception {
		r = RentACat.createInstance(InstanceType.IMPL);

		c1 = Cat.createInstance(InstanceType.IMPL, 1, "Jennyanydots");
		c2 = Cat.createInstance(InstanceType.IMPL, 2, "Old Deuteronomy");
		c3 = Cat.createInstance(InstanceType.IMPL, 3, "Mistoffelees");

		stdout = System.out;
		out = new ByteArrayOutputStream();
		System.setOut(new PrintStream(out));

	}

	@After
	public void tearDown() throws Exception {
		// Restore System.out to the original stdout
		System.setOut(stdout);

		r = null;
		c1 = null;
		c2 = null;
		c3 = null;
	}

	private void addCats() {
		r.addCat(c1);
		r.addCat(c2);
		r.addCat(c3);
	}

	/**
	 * Test case for Cat getCat(int id).
	 * 
	 * <pre>
	 * Preconditions: r has no cats.
	 * Execution steps: Call getCat(2).
	 * Postconditions: Return value is null.
	 *                 System output is "Invalid cat ID." + newline.
	 * </pre>
	 * 
	 * Hint: You will need to use Java reflection to invoke the private getCat(int)
	 * method. efer to the Unit Testing Part 1 lecture and the textbook appendix
	 * hapter on using reflection on how to do this. Please use r.getClass() to get
	 * the class object of r instead of hardcoding it as RentACatImpl.
	 */
	@Test
	public void testGetCatNullNumCats0() {
		// Get the Method object for the method "getCat"
		try {
			Method method = r.getClass().getDeclaredMethod("getCat", int.class);

			// If the method is private, make it accessible
			method.setAccessible(true);

			// Invoke the method on the r instance
			Cat result = (Cat) method.invoke(r, 2);

			// Assert that the result is null
			assertEquals(null, result);
			assertEquals("Invalid cat ID." + newline, out.toString());
		} catch (Exception e) {
			fail("Exception thrown: " + e.getMessage());
		}
	}

	/**
	 * Test case for Cat getCat(int id).
	 * 
	 * <pre>
	 * Preconditions: c1, c2, and c3 are added to r using addCat(Cat c).
	 * Execution steps: Call getCat(2).
	 * Postconditions: Return value is not null.
	 *                 Returned cat has an ID of 2.
	 * </pre>
	 * 
	 * Hint: You will need to use Java reflection to invoke the private getCat(int)
	 * method. efer to the Unit Testing Part 1 lecture and the textbook appendix
	 * hapter on using reflection on how to do this. Please use r.getClass() to get
	 * the class object of r instead of hardcoding it as RentACatImpl.
	 */
	@Test
	public void testGetCatNumCats3() {
		addCats();
		// Get the Class object associated with this object

		// Get the Method object for the method "getCat"
		Method method = null;
		Cat result = null;

		try {
			method = r.getClass().getDeclaredMethod("getCat", int.class);
			method.setAccessible(true);
			result = (Cat) method.invoke(r, 2);
			assertNotNull(result);
			assertEquals(2, result.getId());

		} catch (Exception e) {
			fail("Unexpected exception occurred: " + e.getMessage());
		}
	}

	/**
	 * Test case for String listCats().
	 * 
	 * <pre>
	 * Preconditions: r has no cats.
	 * Execution steps: Call listCats().
	 * Postconditions: Return value is "".
	 * </pre>
	 */
	@Test
	public void testListCatsNumCats0() {
		assertEquals("", r.listCats());
	}

	/**
	 * Test case for String listCats().
	 * 
	 * <pre>
	 * Preconditions: c1, c2, and c3 are added to r using addCat(Cat c).
	 * Execution steps: Call listCats().
	 * Postconditions: Return value is "ID 1. Jennyanydots\nID 2. Old
	 *                 Deuteronomy\nID 3. Mistoffelees\n".
	 * </pre>
	 */
	@Test
	public void testListCatsNumCats3() {
		addCats();
		assertEquals(
				"ID 1. Jennyanydots" + newline + "ID 2. Old Deuteronomy" + newline + "ID 3. Mistoffelees" + newline,
				r.listCats());
	}

	/**
	 * Test case for boolean renameCat(int id, String name).
	 * 
	 * <pre>
	 * Preconditions: r has no cats.
	 * Execution steps: Call renameCat(2, "Garfield").
	 * Postconditions: Return value is false.
	 *                 c2 is not renamed to "Garfield".
	 *                 System output is "Invalid cat ID." + newline.
	 * </pre>
	 */
	@Test
	public void testRenameFailureNumCats0() {
		boolean renameCat = r.renameCat(2, "Garfield");
		assertEquals(false, renameCat);
		assertEquals("Invalid cat ID." + newline, out.toString());
	}

	/**
	 * Test case for boolean renameCat(int id, String name).
	 * 
	 * <pre>
	 * Preconditions: c1, c2, and c3 are added to r using addCat(Cat c).
	 * Execution steps: Call renameCat(2, "Garfield").
	 * Postconditions: Return value is true.
	 *                 c2 is renamed to "Garfield".
	 * </pre>
	 */
	@Test
	public void testRenameNumCat3() {
		addCats();
		boolean renameCat = r.renameCat(2, "Garfield");
		assertEquals(true, renameCat);

		Method method = null;
		Cat result = null;

		try {
			method = r.getClass().getDeclaredMethod("getCat", int.class);
			method.setAccessible(true);
			result = (Cat) method.invoke(r, 2);
		} catch (Exception e) {
			fail("Unexpected exception occurred: " + e.getMessage());
		}
		assertEquals("Garfield", result.getName());
	}

	/**
	 * Test case for boolean rentCat(int id).
	 * 
	 * <pre>
	 * Preconditions: c1, c2, and c3 are added to r using addCat(Cat c).
	 * Execution steps: Call rentCat(2).
	 * Postconditions: Return value is true.
	 *                 c2 is rented as a result of the execution steps.
	 *                 System output is "Old Deuteronomy has been rented." + newline
	 * </pre>
	 */
	@Test
	public void testRentCatNumCats3() {
		addCats();
		boolean rentedCat = r.rentCat(2);
		assertEquals(true, rentedCat);

		Method method = null;
		Cat result = null;

		try {
			method = r.getClass().getDeclaredMethod("getCat", int.class);
			method.setAccessible(true);
			result = (Cat) method.invoke(r, 2);
		} catch (Exception e) {
			fail("Unexpected exception occurred: " + e.getMessage());
		}
		assertEquals(true, result.getRented());
		assertEquals("Old Deuteronomy has been rented." + newline, out.toString());

	}

	/**
	 * Test case for boolean rentCat(int id).
	 * 
	 * <pre>
	 * Preconditions: c1, c2, and c3 are added to r using addCat(Cat c).
	 *                c2 is rented.
	 * Execution steps: Call rentCat(2).
	 * Postconditions: Return value is false.
	 *                 c2 stays rented.
	 *                 System output is "Sorry, Old Deuteronomy is not here!" + newline
	 * </pre>
	 */
	@Test
	public void testRentCatFailureNumCats3() {
		// satisfy precondition
		addCats();
		// rent the cat precondition
		try {
			Method method = null;
			Cat result = null;
			method = r.getClass().getDeclaredMethod("getCat", int.class);
			method.setAccessible(true);
			result = (Cat) method.invoke(r, 2);
			result.rentCat();

		} catch (Exception e) {
			fail("Unexpected exception occurred: " + e.getMessage());
		}

		boolean checkCatReturned = r.returnCat(2);
		assertEquals(true, checkCatReturned);
		assertEquals("Welcome back, Old Deuteronomy!" + newline, out.toString());
	}

	/**
	 * Test case for boolean returnCat(int id).
	 * 
	 * <pre>
	 * Preconditions: c1, c2, and c3 are added to r using addCat(Cat c).
	 *                c2 is rented.
	 * Execution steps: Call returnCat(2).
	 * Postconditions: Return value is true.
	 *                 c2 is returned as a result of the execution steps.
	 *                 System output is "Welcome back, Old Deuteronomy!" + newline
	 * </pre>
	 */
	@Test
	public void testReturnCatNumCats3() {
		// satisfy precondition
		addCats();
		// rent the cat precondition
		try {
			Method method = null;
			Cat result = null;
			method = r.getClass().getDeclaredMethod("getCat", int.class);
			method.setAccessible(true);
			result = (Cat) method.invoke(r, 2);
			result.rentCat();

		} catch (Exception e) {
			fail("Unexpected exception occurred: " + e.getMessage());
		}

		boolean checkCatReturned = r.returnCat(2);
		assertEquals(true, checkCatReturned);
		assertEquals("Welcome back, Old Deuteronomy!" + newline, out.toString());
	}

	// TODO: Fill in

	/**
	 * Test case for boolean returnCat(int id).
	 * 
	 * <pre>
	 * Preconditions: c1, c2, and c3 are added to r using addCat(Cat c).
	 * Execution steps: Call returnCat(2).
	 * Postconditions: Return value is false.
	 *                 c2 stays not rented.
	 *                 System output is "Old Deuteronomy is already here!" + newline
	 * </pre>
	 */
	@Test
	public void testReturnFailureCatNumCats3() {
		addCats();

		boolean checkCatReturned = r.returnCat(2);
		assertEquals(false, checkCatReturned);
		assertEquals("Old Deuteronomy is already here!" + newline, out.toString());
	}

}
