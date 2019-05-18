package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Couple;
import domain.User;

@Service
@Transactional
public class CoupleService {


	// Managed repository -----------------------------------------------------

	// Supporting services ----------------------------------------------------
	@Autowired
	private UserService				userService;
	
	
	public Couple findByUser() {
		User principal;
		
		principal = this.userService.findByPrincipal();
		Assert.notNull(principal);
		
		return principal.getCouple();

	}

}
