package com.devAlbaladejo.PokeCards.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "usercards")
public class Usercards implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	@EmbeddedId
	private UsercardsId id = new UsercardsId();
	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("cardId")
	@JoinColumn(name = "card_id", nullable = false, insertable = false, updatable = false)
	private Cards cards;
	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("userId")
	@JoinColumn(name = "user_id", nullable = false, insertable = false, updatable = false)
	private Users users;
	@Column(name = "amount", nullable = false)
	private int amount;
}
