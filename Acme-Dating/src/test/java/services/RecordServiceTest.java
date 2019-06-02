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
public class RecordServiceTest extends AbstractTest {

	// Service under test

	@Autowired
	private RecordService recordService;
	/*
	 *  Percentage of service tested: 9,1%
	 * 
	 */
	// --------------------------------------------------

	/*
	 * Requirement Tested: Un actor autenticado como usuario (con pareja) debe de ser capaz de:
	 *		1. Gestionar el diario de la pareja
	 */

	// Tests

	/*
	 * En este test se va a probar que solo puede crear una entrada en el diario un actor logueado como user con pareja.
	 */

	@Test
	public void createTestFail() {
		final Object createTest[][] = { {
			
			/*
			 * Test positivo: user con pareja
			 */
			"user1", null,

			/*
			 * Test negativo: User sin pareja
			 */
			"user3", IllegalArgumentException.class,
			
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
			this.recordService.create();
			this.unauthenticate();
			this.recordService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(class1, caught);
	}
}
