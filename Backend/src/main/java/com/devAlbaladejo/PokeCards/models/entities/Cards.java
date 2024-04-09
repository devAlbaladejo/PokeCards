package com.devAlbaladejo.PokeCards.models.entities;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cards")
public class Cards implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "id", unique = true, nullable = false)
	private int id;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "rarity", nullable = false)
	private Rarities rarities;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "primary_type", nullable = false)
	private Types typesByPrimaryType;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "secondary_type")
	private Types typesBySecondaryType;
	@Column(name = "name", nullable = false)
	private String name;
	@Column(name = "image", nullable = false)
	private String image;
	@Column(name = "height", nullable = false)
	private int height;
	@Column(name = "weight", nullable = false)
	private int weight;
	@Column(name = "hp", nullable = false)
	private int hp;
	@Column(name = "attack", nullable = false)
	private int attack;
	@Column(name = "defense", nullable = false)
	private int defense;
	@Column(name = "special_attack", nullable = false)
	private int specialAttack;
	@Column(name = "special_defense", nullable = false)
	private int specialDefense;
	@Column(name = "speed", nullable = false)
	private int speed;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "cards")
	@JsonIgnore
	private Set<Usercards> usercardses = new HashSet<Usercards>(0);

	public Cards(int id, Rarities rarities, Types typesByPrimaryType, String name, String image, int height, int weight,
			int hp, int attack, int defense, int specialAttack, int specialDefense, int speed) {
		this.id = id;
		this.rarities = rarities;
		this.typesByPrimaryType = typesByPrimaryType;
		this.name = name;
		this.image = image;
		this.height = height;
		this.weight = weight;
		this.hp = hp;
		this.attack = attack;
		this.defense = defense;
		this.specialAttack = specialAttack;
		this.specialDefense = specialDefense;
		this.speed = speed;
	}
}
