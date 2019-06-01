
package services;

import javax.transaction.Transactional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml",
})
@Transactional
public class ExperienceServiceTest extends AbstractTest {

	// Service under test

	@Autowired
	private ExperienceService	experienceService;


	/*
	 * Percentage of service tested: 
	 */
	// --------------------------------------------------

	/*
	 * Requirement Tested: Un actor autenticado como empresa debe ser capaz de:
	 *						1. Gestionar las experiencias lo que incluye listarlas, buscar por una palabra clave, crear,
	 *						editarlas y borrarlas.
	 */

	// Tests

	/*
	 * En este test se va a comprobar que unicamente un usuario logueado como company puede crear una experience.
	 * 
	 */

	@Test
	public void createTest() {
		final Object createTest[][] = {
			{
				/*
				 * Test negativo: Autenticado como user
				 */
				"user1", IllegalArgumentException.class,
				
				/*
				 * Test positivo: Autenticado como company
				 */
				"company1", null,

				/*
				 * Test negativo: Autenticado como administrator
				 */
				"admin", IllegalArgumentException.class,

				/*
				 * Test negativo: Sin autenticar
				 */
				null, IllegalArgumentException.class,

			}
		};
		for (int i = 0; i < createTest.length; i++)
			this.CreateTemplate((String) createTest[i][0], (Class<?>) createTest[i][1]);
	}


	// Ancillary methods ------------------------------------------------------

	private void CreateTemplate(final String actor, final Class<?> exception) {
		Class<?> caught;

		caught = null;
		try {
			this.authenticate(actor);
			this.experienceService.create();
			this.unauthenticate();
			this.experienceService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(exception, caught);
	}
}
