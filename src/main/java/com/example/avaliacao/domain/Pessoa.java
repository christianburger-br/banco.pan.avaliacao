package com.example.avaliacao.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "PESSOA")
@NoArgsConstructor
public class Pessoa implements Serializable {

	@Id
	@Getter
	@Setter

	@Column(name="cpf", unique = true, nullable = false)
	private Long cpf;

	/*
	@GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
	@Column(name="id", unique = true, nullable = false)
	@Getter
	@Setter
	Long id;
	*/

	@Getter
	@Column(name="nome")
	String nome;

	@Getter
	@Column(name="sobrenome")
	String sobrenome;

	@Getter
	@Setter
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "endereco_id", referencedColumnName = "id")
	private Endereco endereco;

	public Pessoa(long cpf, String nome, String sobrenome, Endereco endereco) {
		this.cpf= cpf;
		this.nome= nome;
		this.sobrenome= sobrenome;
		this.endereco= endereco;
	}

	public Pessoa(Pessoa pessoa) {
		this.cpf= pessoa.cpf;
		this.nome= pessoa.nome;
		this.sobrenome= pessoa.sobrenome;
		this.endereco= pessoa.endereco;
	}
}

