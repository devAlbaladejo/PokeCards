package com.devAlbaladejo.PokeCards.models.entities;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "exchange_offers")
public class ExchangeOffers implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private int id;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id_offer", nullable = false)
	private Users userOffer;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "card_id_offer", nullable = false)
	private Cards cardOffer;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "desired_card_1", nullable = false)
	private Cards desiredCard1;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "desired_card_2")
	private Cards desiredCard2;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "desired_card_3")
	private Cards desiredCard3;
	@Column(name = "active", unique = true, nullable = false)
	private int active;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "exchangeOffer")
	@JsonIgnore
	private Set<Exchanges> exchangeses = new HashSet<Exchanges>(0);

	public ExchangeOffers(int id, Cards desiredCard1, Cards cardOffer, Users userOffer, int active) {
		this.id = id;
		this.desiredCard1 = desiredCard1;
		this.cardOffer = cardOffer;
		this.userOffer = userOffer;
		this.active = active;
	}
}
