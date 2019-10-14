package com.example.avaliacao.domain;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.Locale;

@Slf4j
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Account {
	/**Numero da conta.*/
	@Id
	@Getter
	@GeneratedValue(strategy= GenerationType.AUTO)
	private long accountID;
    //private final long accountID;

	@Getter
    @Transient
	private Pessoa pessoa;

	@Getter
	@Setter
	private double saldo;

	// simulando geracao de novo numero de conta.
	public static Account gerarNovaConta () {
	    String nome= "Mariana";
		String sobrenome= "Rodrigues";
		long cpf= 4255169099L;
		String cep= "04521902";
		String logradouro= "Rua Canario, 980, apt 201";
		String bairro= "Moema";
		String municipio= "Sao Paulo";
		String estado= "SP";
		String pais= "Brazil";

		//long accountID= (long) (Math.random() * 10000000);
		BigInteger accountID= null;
		log.info(String.format(Locale.getDefault(), "Account.gerarNovaConta:accountID: %s", accountID));
		double saldo= 0;
		Endereco endereco= new Endereco(cpf, cep, logradouro, bairro, municipio, estado, pais);
		Pessoa pessoa= new Pessoa(cpf, nome, sobrenome, endereco);
		return new Account(0, pessoa, saldo);
	}

	public static Account gerarNovaConta ( Pessoa pessoa) {
		log.info(String.format(Locale.getDefault(), "Account.gerarNovaConta: pessoa != null: %s", pessoa != null ));
		double saldo= 0;
		//Pessoa pessoa= Pessoa.gerarNovaPessoa(nome, sobrenome, cpf, endereco.getEndereco_cep(), endereco.getEndereco_logradouro(), endereco.getEndereco_bairro(), endereco.getEndereco_municipio(), endereco.getEndereco_estado(), endereco.getEndereco_pais());
		return new Account(0, pessoa, saldo);
	}

	public static Account gerarNovaConta ( String nome, String sobrenome, long cpf, Endereco endereco ) {
		//long accountID= (long) (Math.random() * 10000000);
		BigInteger accountID= null;
		log.info(String.format(Locale.getDefault(), "Account.gerarNovaConta:accountID: %s", accountID));
		double saldo= 0;
		//Pessoa pessoa= Pessoa.gerarNovaPessoa(nome, sobrenome, cpf, endereco.getEndereco_cep(), endereco.getEndereco_logradouro(), endereco.getEndereco_bairro(), endereco.getEndereco_municipio(), endereco.getEndereco_estado(), endereco.getEndereco_pais());
		Pessoa pessoa= new Pessoa(cpf, nome, sobrenome, endereco);
		return new Account(0, pessoa, saldo);
	}

	public static Account gerarNovaConta ( String nome, String sobrenome, long cpf,
										   String cep, String logradouro, String bairro, String municipio, String estado, String pais ) {
		//long accountID= (long) (Math.random() * 10000000);
		BigInteger accountID= null;
		log.info(String.format(Locale.getDefault(), "Account.gerarNovaConta:accountID: %s", accountID));
		double saldo= 0;
		Endereco endereco= new Endereco(cpf, cep, logradouro, bairro, municipio, estado, pais);
		Pessoa pessoa= new Pessoa(cpf, nome, sobrenome, endereco);
		return new Account(0, pessoa, saldo);
	}
}
