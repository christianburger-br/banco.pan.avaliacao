package com.example.avaliacao.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
public class Endereco {
	@Id
	@Column(name="id", unique = true, nullable = false)
	@Getter
	private Long cpf;

	@OneToOne(mappedBy = "endereco")
	private Pessoa pessoa;

	@Getter
	@Setter
	String cep;
	@Getter
	@Setter
	String logradouro;
	@Getter
	@Setter
	String bairro;
	@Getter
	@Setter
	String municipio;
	@Getter
	@Setter
	String estado;
	@Getter
	@Setter
	String pais;

	public Endereco(long cpf, String cep, String logradouro, String bairro, String municipio, String estado, String pais) {
	    this.cpf= cpf;
		this.cep= cep;
		this.logradouro= logradouro;
		this.bairro= bairro;
		this.municipio= municipio;
		this.estado= estado;
		this.pais= pais;
	}

	public Endereco(Endereco endereco1) {
		this(endereco1.cpf, endereco1.cep, endereco1.logradouro, endereco1.bairro, endereco1.municipio, endereco1.estado, endereco1.pais);
	}
}

