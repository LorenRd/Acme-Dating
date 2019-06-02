package services;

import javax.transaction.Transactional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/junit.xml", })
@Transactional
public class CoupleRequestServiceTest extends AbstractTest {

	// Service under test

	@Autowired
	private CoupleRequestService coupleRequestService;
	/*
	 *  Percentage of service tested:
	 * 
	 */
	// --------------------------------------------------

	/*
	 * Requirement Tested: 17. Un usuario (sin pareja) debe de ser capaz de:
	 *	1. Enviar invitaciones para formar parejas
	 */

	// Tests

	/*
	 * En este test se va a probar que solo puede enviar una coupleRequest un usuario sin pareja.
	 */

	@Test
	public void createTestFail() {
		final Object createTest[][] = { {
			/*
			 * Test positivo: User sin pareja
			 */
			"user3", null,
			
			/*
			 * Test negativo: user con pareja
			 */
			"user1", IllegalArgumentException.class,

			/*
			 * Test negativo: Company
			 */
			"company1", IllegalArgumentException.class,

		
			} 
		
		};
		
		
		for (int i = 0; i < createTest.length; i++)
			this.CreateTemplate((String) createTest[i][0], (Class<?>) createTest[i][1]);
	}


	
	
	// Ancillary methods ------------------------------------------------------

	private void CreateTemplate(final String actor, final Class<?> class1) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(actor);
			this.coupleRequestService.create();
			this.unauthenticate();
			this.coupleRequestService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(class1, caught);
	}
}
