
package services;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.SocialNetworkRepository;
import domain.SocialNetwork;
import domain.User;

@Service
@Transactional
public class SocialNetworkService {

	@Autowired
	private UserService				userService;

	@Autowired
	private Validator				validator;

	@Autowired
	private SocialNetworkRepository	socialNetworkRepository;


	public List<SocialNetwork> findAll() {
		return this.socialNetworkRepository.findAll();
	}

	public SocialNetwork findOne(final Integer id) {
		return this.socialNetworkRepository.findOne(id);
	}

	public boolean exists(final Integer id) {
		return this.socialNetworkRepository.exists(id);
	}

	public Collection<SocialNetwork> findByUserId(final int id) {
		Collection<SocialNetwork> result;
		result = this.socialNetworkRepository.findByUserId(id);
		return result;
	}

	public SocialNetwork reconstructPruned(final SocialNetwork socialNetwork, final BindingResult binding) {
		SocialNetwork result;
		final User user = this.userService.findByPrincipal();
		if (socialNetwork.getId() == 0) {
			result = socialNetwork;
			result.setUser(user);
		}

		else
			result = this.socialNetworkRepository.findOne(socialNetwork.getId());
		result.setName(socialNetwork.getName());
		result.setLinkProfile(socialNetwork.getLinkProfile());

		this.validator.validate(result, binding);
		this.socialNetworkRepository.flush();

		return result;
	}

	public SocialNetwork save(final SocialNetwork socialNetwork) {
		final SocialNetwork result;
		if (socialNetwork.getId() != 0) {
			final User user = this.userService.findByPrincipal();
			Assert.isTrue(socialNetwork.getUser().getId() == user.getId());
		}
		result = this.socialNetworkRepository.save(socialNetwork);
		Assert.notNull(result);

		return null;
	}

	public SocialNetwork create() {
		SocialNetwork result;
		result = new SocialNetwork();
		final User user = this.userService.findByPrincipal();
		result.setUser(user);
		return result;
	}

}
