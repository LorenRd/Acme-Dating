package services;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import domain.User;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/junit.xml", })
@Transactional
public class UserServiceTest extends AbstractTest {

	// Service under test

	@Autowired
	private UserService userService;
	/*
	 *  Percentage of service tested: 19,8%
	 * 
	 */
	// --------------------------------------------------

	/*
	 * Requirement Tested: Un actor no autenticado debe ser capaz de:
	 *						1. Registrarse como usuario 
	 */

	// Tests

	/*
	 * En este test se va a probar que se deben rellenar los campos obligatorios
	 * para registrarse como user
	 */

	@Test
	public void createTestFail() {
		final Object createTest[][] = { {
					/*
					 * Test negativo: Falta introducir un nombre
					 */
					null, "", "Elena", "javierelena@gmail.com",
					"fraelefer", "fraelefer", "+34912345567",
					"https://www.informatica.us.es/docs/imagen-etsii/MarcaUS.png",
					"Francisco Elena", "MasterCard", "5220 2777 7103 1876",
					9, 23, 627, ConstraintViolationException.class,
					
					/*
					 * Test negativo: La foto no es una URL valida
					 */				
					
					null, "Javier", "Elena", "javierelena@gmail.com",
					"fraelefer", "fraelefer", "+34912345567",
					"ESTO NO ES UNA URL",
					"Francisco Elena", "MasterCard", "5220 2777 7103 1876",
					9, 23, 627, ConstraintViolationException.class,
			
		
			} 
		
		};
		
		
		for (int i = 0; i < createTest.length; i++)
			this.CreateTemplate((String) createTest[i][0],
					(String) createTest[i][1], (String) createTest[i][2],
					(String) createTest[i][3], (String) createTest[i][4],
					(String) createTest[i][5], (String) createTest[i][6],
					(String) createTest[i][7],
					(String) createTest[i][8], (String) createTest[i][9],
					(String) createTest[i][10], (int) createTest[i][11],
					(int) createTest[i][12], (int) createTest[i][13],(Class<?>) createTest[i][14]);
	}

	@Test
	public void createTestSuccess() {
		final Object createTest[][] = { {
		/*
		 * Test positivo:
		 */
		null, "Javier", "Fernández",
				"javierelena@gmail.com", "javferele", "javferele",
				"+34912345567",
				"https://www.informatica.us.es/docs/imagen-etsii/MarcaUS2.png",
				"Javier Fernández", "MasterCard", "5220 2777 7103 1876",
				11, 25, 227, null } };
		for (int i = 0; i < createTest.length; i++)
			this.CreateTemplate((String) createTest[i][0],
					(String) createTest[i][1], (String) createTest[i][2],
					(String) createTest[i][3], (String) createTest[i][4],
					(String) createTest[i][5], (String) createTest[i][6],
					(String) createTest[i][7],
					(String) createTest[i][8], (String) createTest[i][9],
					(String) createTest[i][10], (int) createTest[i][11],
					(int) createTest[i][12], (int) createTest[i][13],(Class<?>) createTest[i][14]);
	}
	
	
	// --------------------------------------------------

	/*
	 * Requirement Tested: Un actor autenticado en el sistema debe ser capaz de:
	 * 						2. Editar su información personal.
	 */
	@Test
	public void editTestSuccess() {
		final Object editTest[][] = { {
				/*
				 * Test positivo:
				 */
					"user1", "NOMBRE CAMBIADO","http://www.foto.com", null,
					
				/*
				 * Test negativo: Dejo el nombre en blanco
				 */
					"user1", "","http://www.foto.com", ConstraintViolationException.class,
					
				/*
				 * Test negativo: La foto de perfil no es una url
				 */
					"user1", "NOMBRE CAMBIADO", "NO ES URL",ConstraintViolationException.class,
					
											
					
			} 
		
		};
		for (int i = 0; i < editTest.length; i++)
			this.EditTemplate((String) editTest[i][0],(String) editTest[i][1],(String) editTest[i][2],(Class<?>) editTest[i][3]);
	}
	
	
	
	
	
	// Ancillary methods ------------------------------------------------------

	private void CreateTemplate(final String actor, final String name,
			final String surname, final String email,
			final String username, final String password, final String phone,
			final String photo,
			final String holderName, final String brandName,
			final String number, final int month, final int year,
			final int cvv, final Class<?> class1) {
		Class<?> caught;
		User user;

		caught = null;
		try {
			user = this.userService.create();
			user.setName(name);
			user.setSurname(surname);
			user.setEmail(email);
			user.getUserAccount().setUsername(username);
			user.getUserAccount().setPassword(password);
			user.setPhone(phone);
			user.setPhoto(photo);
			user.getCreditCard().setHolderName(holderName);
			user.getCreditCard().setBrandName(brandName);
			user.getCreditCard().setCVV(cvv);
			user.getCreditCard().setExpirationMonth(month);
			user.getCreditCard().setExpirationYear(year);
			user.getCreditCard().setNumber(number);
			final User saved = this.userService.save(user);
			this.userService.flush();
			Assert.notNull(this.userService.findOne(saved.getId()));
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(class1, caught);
	}
	
	private void EditTemplate(final String actor, final String nombre, final String foto, final Class<?> exception) {
		Class<?> caught;
		User user;
		caught = null;
		try {
			this.authenticate(actor);
			user = this.userService.findByPrincipal();
			user.setName(nombre);
			user.setPhoto(foto);
			this.userService.save2(user);
			this.unauthenticate();
			this.userService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(exception, caught);
	}
}
