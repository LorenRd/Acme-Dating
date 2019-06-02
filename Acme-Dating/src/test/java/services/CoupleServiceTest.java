package services;

import javax.transaction.Transactional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import domain.Couple;
import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/junit.xml", })
@Transactional
public class CoupleServiceTest extends AbstractTest {

	// Service under test

	@Autowired
	private CoupleService coupleService;
	/*
	 *  Percentage of service tested:
	 * 
	 */
	// --------------------------------------------------

	/*
	 * Requirement Tested: Un actor autenticado como usuario (con pareja) debe de ser capaz de:
	 *		6. Borrar la pareja.
	 */

	// Tests

	/*
	 * En este test se va a probar que solo puede borrar una pareja un usuario con pareja.
	 */

	@Test
	public void deleteTest() {
		final Object deleteTest[][] = { {
			/*
			 * Test negativo: User sin pareja
			 */
			"user3", IllegalArgumentException.class,
			
			/*
			 * Test positivo: user con pareja
			 */
			"user1", null,

			/*
			 * Test negativo: Company
			 */
			"company1", IllegalArgumentException.class,

		
			} 
		
		};
		
		
		for (int i = 0; i < deleteTest.length; i++)
			this.DeleteTemplate((String) deleteTest[i][0], (Class<?>) deleteTest[i][1]);
	}


	
	
	// Ancillary methods ------------------------------------------------------

	private void DeleteTemplate(final String actor, final Class<?> class1) {
		Class<?> caught;
		Couple couple;
		caught = null;
		try {
			this.authenticate(actor);
			couple = this.coupleService.findByUser();
			this.coupleService.delete(couple);
			this.unauthenticate();
			this.coupleService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(class1, caught);
	}
}
