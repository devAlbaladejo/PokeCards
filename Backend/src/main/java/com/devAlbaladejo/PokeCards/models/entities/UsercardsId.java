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
@Embeddable
public class UsercardsId implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	@Column(name = "user_id", nullable = false)
	private int userId;
	@Column(name = "card_id", nullable = false)
	private int cardId;

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof UsercardsId))
			return false;
		UsercardsId castOther = (UsercardsId) other;

		return (this.getUserId() == castOther.getUserId()) && (this.getCardId() == castOther.getCardId());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getUserId();
		result = 37 * result + this.getCardId();
		return result;
	}

}
