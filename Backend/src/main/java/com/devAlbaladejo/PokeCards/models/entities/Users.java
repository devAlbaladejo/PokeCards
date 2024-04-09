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
@Table(name = "users")
public class Users implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private int id;
	@Column(name = "username", nullable = false)
	private String username;
	@Column(name = "email", nullable = false)
	private String email;
	@Column(name = "password", nullable = false)
	private String password;
	@Column(name = "points", nullable = false)
	private int points;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "users")
	@JsonIgnore
	private Set<Usercards> usercardses = new HashSet<Usercards>(0);

	public Users(int id, String username, String email, String password, int points) {
		this.id = id;
		this.username = username;
		this.email = email;
		this.password = password;
		this.points = points;
	}
}
