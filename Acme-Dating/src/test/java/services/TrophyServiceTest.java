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
public class TrophyServiceTest extends AbstractTest {

	// Service under test

	@Autowired
	private TrophyService trophyService;
	/*
	 *  Percentage of service tested:
	 * 
	 */
	// --------------------------------------------------

	/*
	 * Requirement Tested: Un actor autenticado como administrador debe de ser capaz de:
	 *		2. Gestionar el catálogo de trofeos
	 */

	// Tests

	/*
	 * En este test se va a probar que solo un administrador puede crear un trofeo.
	 */

	@Test
	public void createTestFail() {
		final Object createTest[][] = { {
			/*
			 * Test positivo: Administrador
			 */
			"admin", null,
			
			/*
			 * Test positivo: User
			 */
			"user1",IllegalArgumentException.class,

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
			this.trophyService.create();
			this.unauthenticate();
			this.trophyService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(class1, caught);
	}
}
