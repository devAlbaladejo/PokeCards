package com.devAlbaladejo.PokeCards.models.entities;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
@Table(name = "types")
public class Types implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private int id;
	@Column(name = "name", nullable = false)
	private String name;
	@Column(name = "color", nullable = false)
	private String color;
	@Column(name = "strong")
	private String strong;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "primaryType")
	@JsonIgnore
	private Set<Cards> cardsesForPrimaryType = new HashSet<Cards>(0);
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "secondaryType")
	@JsonIgnore
	private Set<Cards> cardsesForSecondaryType = new HashSet<Cards>(0);

	public Types(int id, String name, String color) {
		this.id = id;
		this.name = name;
		this.color = color;
	}
}
