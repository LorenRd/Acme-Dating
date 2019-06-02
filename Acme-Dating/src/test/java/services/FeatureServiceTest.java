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
public class FeatureServiceTest extends AbstractTest {

	// Service under test

	@Autowired
	private FeatureService featureService;
	/*
	 *  Percentage of service tested: 11,4%
	 * 
	 */
	// --------------------------------------------------

	/*
	 * Requirement Tested: Un actor autenticado como empresa debe ser capaz de:
	 *		4. Gestionar los extras.
	 */

	// Tests

	/*
	 * En este test se va a probar que solo puede crear un extra un usuario logueado como company
	 */

	@Test
	public void createTestFail() {
		final Object createTest[][] = { {
			/*
			 * Test positivo: Company
			 */
			"company1", null,

			/*
			 * Test negativo: User
			 */
			"user1", IllegalArgumentException.class,
			
			/*
			 * Test negativo: admin
			 */
			"admin", IllegalArgumentException.class,


		
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
			this.featureService.create();
			this.unauthenticate();
			this.featureService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(class1, caught);
	}
}
