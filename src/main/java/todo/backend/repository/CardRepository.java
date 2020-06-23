package todo.backend.repository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import todo.backend.entity.Card;
import todo.backend.entity.CardStatus;

@Component
public class CardRepository {

	private final Map<Long, Card> cachedCards = new HashMap<>();

	public List<Card> findAll() {
		return new ArrayList<>(cachedCards.values());
	}

	public List<Card> findAllOrderByPriorityAsc() {
		return findAll().stream()
			.sorted(Comparator.comparingInt(Card::getPriority))
			.collect(Collectors.toList());
	}

	public Card save(Card card) {
		Long id = (long)cachedCards.size() + 1;
		card.setId(id);
		card.setStatus(CardStatus.TODO);
		return cachedCards.put(id, card);
	}

	public Card update(Long id, Card card) {
		Card savedCard = cachedCards.get(id);
		savedCard.setStatus(card.getStatus());
		return savedCard;
	}

	public void deleteById(Long id) {
		cachedCards.remove(id);
	}

	public boolean doesCardExist(Long id) {
		return cachedCards.containsKey(id);
	}

}
