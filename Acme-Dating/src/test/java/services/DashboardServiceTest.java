
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml",
})
@Transactional
public class DashboardServiceTest extends AbstractTest {

	// Service under test

	@Autowired
	private ExperienceService	experienceService;


	/*
	 * Percentage of service tested: 
	 */

	// --------------------------------------------------

	// Tests

	/*
	 * Requirement Tested:
	 * Un actor autenticado como administrador debe de ser capaz de:
	 * 8. Visualizar el panel de control del sistema:
	 * 
	 * En este test probamos que unicamente un usuario logueado como admin puede hacer uso
	 * de los servicios del dashboard.
	 * Creamos un driver con distintos tipos de usuarios y lo probamos.
	 */

	@Test
	public void authorityTest() {
		final Object authorityTest[][] = {
			{
				"admin", null
			}, {
				"user1", IllegalArgumentException.class
			}, {
				"company1", IllegalArgumentException.class
			}, {
				null, IllegalArgumentException.class
			}
		};
		for (int i = 0; i < authorityTest.length; i++)
			this.AuthorityTemplate((String) authorityTest[i][0], (Class<?>) authorityTest[i][1]);
	}

	// Ancillary methods ------------------------------------------------------

	private void AuthorityTemplate(final String string, final Class<?> class1) {
		Class<?> caught;

		caught = null;
		try {
			this.authenticate(string);
			this.experienceService.avgExperiencesPerCompany();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(class1, caught);
	}

	/*
	 * Requirement Tested:
	 * Probamos que los resultados devueltos por los test del calculo de estadisticas
	 * sean los esperados en base a nuestro populate.
	 */

	@Test
	public void valueTest() {
		final Object valueTest[][] = {
			{
				"admin", "avg", 1.0, null
			}, {
				"admin", "min", 1.0, null
			}, {
				"admin", "max", 1.0, null
			}, {
				"admin", "stddev", 0.0, null
			}, {
				"user1", "avg", 1.0, IllegalArgumentException.class
			}, {
				"user1", "min", 0.0, IllegalArgumentException.class
			}, {
				"user1", "max", 4.0, IllegalArgumentException.class
			}, {
				"user1", "stddev", 1.41421, IllegalArgumentException.class
			}, {
				"company1", "avg", 1.0, IllegalArgumentException.class
			}, {
				"company1", "min", 0.0, IllegalArgumentException.class
			}, {
				"company1", "max", 4.0, IllegalArgumentException.class
			}, {
				"company1", "stddev", 1.41421, IllegalArgumentException.class
			}
		};
		for (int i = 0; i < valueTest.length; i++)
			this.ValueTemplate((String) valueTest[i][0], (String) valueTest[i][1], (Double) valueTest[i][2], (Class<?>) valueTest[i][3]);

	}

	private void ValueTemplate(final String actor, final String string, final Double double1, final Class<?> class1) {
		Class<?> caught;

		caught = null;
		Double value = 0.0;
		try {
			this.authenticate(actor);
			if (string == "avg") {
				value = this.experienceService.avgExperiencesPerCompany();
				Assert.isTrue(value.equals(double1));
			} else if (string == "min") {
				value = this.experienceService.minExperiencesPerCompany();
				Assert.isTrue(value.equals(double1));
			} else if (string == "max") {
				value = this.experienceService.maxExperiencesPerCompany();
				Assert.isTrue(value.equals(double1));
			} else if (string == "stddev") {
				value = this.experienceService.stddevExperiencesPerCompany();
				Assert.isTrue(value.equals(double1));
			}
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(class1, caught);
	}
}
