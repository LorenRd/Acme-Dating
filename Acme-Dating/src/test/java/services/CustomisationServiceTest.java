package services;

import javax.transaction.Transactional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import domain.Customisation;
import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/junit.xml", })
@Transactional
public class CustomisationServiceTest extends AbstractTest {

	// Service under test

	@Autowired
	private CustomisationService customisationService;
	/*
	 *  Percentage of service tested:
	 * 
	 */
	// --------------------------------------------------

	/*
	 * Requirement Tested: Un actor autenticado como administrador debe de ser capaz de:
	 *		7. Personalizar el sistema
	 */

	// Tests

	/*
	 * En este test se va a probar que solo un administrador puede crear un trofeo.
	 */

	@Test
	public void editTestFail() {
		final Object editTest[][] = { {
			/*
			 * Test positivo: Administrador
			 */
			"admin","http://www.imagen.com", null,
			
			/*
			 * Test negativo: User
			 */
			"user1","http://www.imagen.com",IllegalArgumentException.class,

			/*
			 * Test negativo: Dejando un campo en blanco
			 */
			"admin","", IllegalArgumentException.class,

		
			} 
		
		};
		
		
		for (int i = 0; i < editTest.length; i++)
			this.EditTemplate((String) editTest[i][0],(String) editTest[i][1], (Class<?>) editTest[i][2]);
	}


	
	
	// Ancillary methods ------------------------------------------------------

	private void EditTemplate(final String actor,final String banner, final Class<?> class1) {
		Class<?> caught;
		caught = null;
		Customisation customisation;
		try {
			this.authenticate(actor);
			customisation = this.customisationService.find();
			customisation.setWelcomeBanner(banner);
			this.customisationService.save(customisation);
			this.unauthenticate();
			this.customisationService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(class1, caught);
	}
}
