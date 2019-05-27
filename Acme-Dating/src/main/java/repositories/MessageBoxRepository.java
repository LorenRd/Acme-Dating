
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.MessageBox;

@Repository
public interface MessageBoxRepository extends JpaRepository<MessageBox, Integer> {

	@Query("select m from MessageBox m, Actor a  where (m member of a.messageBoxes and m.name='in box' and a.id=?1)")
	MessageBox findInBoxMessageBoxByActorId(int actorId);

	@Query("select m from MessageBox m, Actor a  where (m member of a.messageBoxes and m.name='out box' and a.id=?1)")
	MessageBox findOutBoxMessageBoxByActorId(int actorId);

	@Query("select m from MessageBox m, Actor a  where (m member of a.messageBoxes and m.name='notification box' and a.id=?1)")
	MessageBox findNotificationMessageBoxByActorId(int actorId);

	@Query("select b from MessageBox b where b.actor.id = ?1")
	Collection<MessageBox> findAllByActorId(int actorId);

	@Query("select b from MessageBox b join b.messages m where m.id =?1")
	Collection<MessageBox> findAllByMessageId(int messageId);

}
