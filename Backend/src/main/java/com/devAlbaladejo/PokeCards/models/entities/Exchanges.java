package com.devAlbaladejo.PokeCards.models.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "exchanges")
public class Exchanges implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private int id;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "exchange_offer_id", nullable = false)
	private ExchangeOffers exchangeOffer;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id_demand", nullable = false)
	private Users userDemand;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "card_id_demand", nullable = false)
	private Cards cardDemand;
}
