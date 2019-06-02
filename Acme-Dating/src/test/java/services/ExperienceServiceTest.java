
package services;

import javax.transaction.Transactional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import domain.Experience;
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
	 * Percentage of service tested: 37,3%
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
				 * Test positivo: Autenticado como company
				 */
				"company1", null,

				/*
				 * Test negativo: Autenticado como user
				 */
				"user1", IllegalArgumentException.class,
				

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

	// Tests

	/*
	 * En este test se va a comprobar que unicamente un usuario logueado como company puede borrar una experience,
	 * además únicamente podrá borrarla la company dueña de esa experience.
	 * 
	 */

	@Test
	public void deleteTest() {
		final Object deleteTest[][] = {
			{
				
				/*
				 * Test positivo: Autenticado como company y dueña de la experience
				 */
				"company2","experience2", null,

				/*
				 * Test negativo: Autenticado como user
				 */
				"user1","experience2", IllegalArgumentException.class,
				
				/*
				 * Test negativo: Autenticado como company, pero no dueña de esa experience
				 */
				"company1","experience2", IllegalArgumentException.class,

				/*
				 * Test negativo: Autenticado como administrator
				 */
				"admin","experience2", IllegalArgumentException.class,

				/*
				 * Test negativo: Sin autenticar
				 */
				null,"experience2", IllegalArgumentException.class,
				
			}
		};
		for (int i = 0; i < deleteTest.length; i++)
			this.DeleteTemplate((String) deleteTest[i][0], (String) deleteTest[i][1],(Class<?>) deleteTest[i][2]);
	}
	
	/*
	 * En este test se va a comprobar que unicamente un usuario logueado como company puede editar una experience,
	 * además únicamente podrá editarla la company dueña de esa experience.
	 * 
	 * Haremos también la prueba de dejar la descripción vacía.
	 */

	@Test
	public void updateTest() {
		final Object updateTest[][] = {
			{
				/*
				 * Test positivo: Autenticado como company y dueña de la experience
				 */
				"company2","experience2","other description", null,

				/*
				 * Test negativo: Autenticado como user
				 */
				"user1","experience2","other description", IllegalArgumentException.class,
				
				/*
				 * Test negativo: Autenticado como company, pero no dueña de esa experience
				 */
				"company1","experience2","other description", IllegalArgumentException.class,

				/*
				 * Test negativo: Autenticado como administrator
				 */
				"admin","experience2","other description", IllegalArgumentException.class,

				/*
				 * Test negativo: Sin autenticar
				 */
				null,"experience2","other description", IllegalArgumentException.class,
				/*
				 * Test negativo: Autenticado como company y dueña de la experience,
				 * 
				 * Deja la descripción vacía.
				 */
				"company2","experience2","", IllegalArgumentException.class,

			}
		};
		for (int i = 0; i < updateTest.length; i++)
			this.UpdateTemplate((String) updateTest[i][0], (String) updateTest[i][1],(String) updateTest[i][2],(Class<?>) updateTest[i][3]);
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
	
	private void DeleteTemplate(final String actor, final String thing,final Class<?> exception) {
		Class<?> caught;
		Experience experience;
		caught = null;
		try {
			this.authenticate(actor);
			experience = this.experienceService.findOne(this.getEntityId(thing));
			this.experienceService.delete(experience);
			this.unauthenticate();
			this.experienceService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(exception, caught);
	}
	
	
	private void UpdateTemplate(final String actor, final String thing, final String description, final Class<?> exception) {
		Class<?> caught;
		Experience experience;
		caught = null;
		try {
			this.authenticate(actor);
			experience = this.experienceService.findOne(this.getEntityId(thing));
			experience.setBody(description);
			this.experienceService.save(experience);
			this.unauthenticate();
			this.experienceService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(exception, caught);
	}
}
