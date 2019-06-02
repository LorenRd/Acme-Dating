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
public class TaskServiceTest extends AbstractTest {

	// Service under test

	@Autowired
	private TaskService taskService;
	/*
	 *  Percentage of service tested:
	 * 
	 */
	// --------------------------------------------------

	/*
	 * Requirement Tested: Un actor autenticado como usuario (con pareja) debe de ser capaz de:
	 *		4. Gestionar las tareas pendientes.
	 */

	// Tests

	/*
	 * En este test se va a probar que solo puede crear una tarea un usuario con pareja.
	 */

	@Test
	public void createTestFail() {
		final Object createTest[][] = { {
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
		
		
		for (int i = 0; i < createTest.length; i++)
			this.CreateTemplate((String) createTest[i][0], (Class<?>) createTest[i][1]);
	}


	
	
	// Ancillary methods ------------------------------------------------------

	private void CreateTemplate(final String actor, final Class<?> class1) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(actor);
			this.taskService.create();
			this.unauthenticate();
			this.taskService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(class1, caught);
	}
}
