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
public class ExperienceCommentServiceTest extends AbstractTest {

	// Service under test

	@Autowired
	private ExperienceCommentService experienceCommentService;
	/*
	 *  Percentage of service tested:
	 * 
	 */
	// --------------------------------------------------

	/*
	 * Requirement Tested: Un actor autenticado como usuario (con pareja) debe de ser capaz de:
	 *		3. Puede comentar en forma de respuesta o ayuda a las dudas de sus clientes.
	 */

	// Tests

	/*
	 * En este test se va a probar que solo puede crear una tarea un usuario con pareja.
	 */

	@Test
	public void createTestFail() {
		final Object createTest[][] = { {
			/*
			 * Test negativo: Sin estar autenticado
			 */
			null,"experience1", IllegalArgumentException.class,
			
			/*
			 * Test positivo: Siendo company
			 */
			"company1","experience1", null,

			/*
			 * Test negativo: Experience inexistente
			 */
			"company1","experience10", IllegalArgumentException.class,

		
			} 
		
		};
		
		
		for (int i = 0; i < createTest.length; i++)
			this.CreateTemplate((String) createTest[i][0], (String) createTest[i][1],(Class<?>) createTest[i][2]);
	}


	
	
	// Ancillary methods ------------------------------------------------------

	private void CreateTemplate(final String actor,final String thing, final Class<?> class1) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(actor);
			this.experienceCommentService.create(true,this.getEntityId(thing));
			this.unauthenticate();
			this.experienceCommentService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(class1, caught);
	}
}
