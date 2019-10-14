package com.example.avaliacao;

import com.example.avaliacao.domain.Endereco;
import com.example.avaliacao.domain.Pessoa;
import com.example.avaliacao.exceptions.PessoaJaExisteException;
import com.example.avaliacao.exceptions.PessoaNaoExisteException;
import com.example.avaliacao.repositories.PessoaRepository;
import com.example.avaliacao.service.ExternalService;
import com.example.avaliacao.service.PessoaService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AvaliacaoApplicationTests {

	@Test
	public void contextLoads() {
	}

	/** Teste de PessoaService:
	 * 	public Optional<Pessoa> findById( long id)
	 * public List<Pessoa> findAll()
	 * public Pessoa update(Pessoa pessoa) throws Exception
	 * public Pessoa save(Pessoa pessoa) throws Exception {
	 */

	@Autowired
	private PessoaService pessoaService;

	@Autowired
	private ExternalService externalService;

	@MockBean
	private PessoaRepository pessoaRepository;

	@Test
	public void getClientesTest() {
		Endereco endereco1= new Endereco();
		Endereco endereco2= new Endereco();

		Pessoa pessoa1= new Pessoa(376, "Danielle", "McArthur", endereco1);
		Pessoa pessoa2= new Pessoa ( 958, "Carlos", "Pessoa", endereco2);

		when(pessoaRepository.findAll()).thenReturn(Stream.of(pessoa1,pessoa2).collect(Collectors.toList()));
		assertEquals(2, pessoaService.findAll().size());
	}

	@Test
	public void getUserbyCPFTest() {
		long cpf= 435085;
		Endereco endereco1= new Endereco();
		Endereco endereco2= new Endereco();

		Pessoa pessoa1= new Pessoa(435085, "Danielle", "McArthur", endereco1);
		Pessoa pessoa2= new Pessoa ( 958, "Carlos", "Pessoa", endereco2);

		when(pessoaRepository.findById(cpf)).thenReturn(java.util.Optional.of(pessoa1));
		assertEquals(java.util.Optional.of(pessoa1), pessoaService.findById(cpf));
	}

	@Test
	public void savePessoaTest() throws Exception {
		Endereco endereco1= new Endereco();
		Endereco endereco2= new Endereco();

		Pessoa pessoa = new Pessoa(999, "Pranya", "Udzkha", endereco2);
		when(pessoaRepository.save(pessoa)).thenReturn(pessoa);
		assertEquals(pessoa, pessoaService.save(pessoa));
	}


	@Test
	public void updatePessoaTest() throws Exception {

		String cep, logradouro, bairro, municipio, estado, pais;
		long cpf= 900887L;
		cep="04520-013";
		logradouro="Rua Inhambú, 952 apt 61";
		bairro= "Vila Uberabinha";
		municipio= "Sao Paulo";
		estado= "SP";
		pais= "Brazil";

		Endereco endereco1= new Endereco(cpf, cep, logradouro, bairro, municipio, estado, pais);
		Endereco endereco2= new Endereco(endereco1);
		logradouro="Rua Canario, 852 apt 82";
		cep="04521-004";
		endereco2.setLogradouro(logradouro);
		endereco2.setCep(cep);

		Pessoa pessoa = new Pessoa(cpf, "Pranya", "Udzkha", endereco1);
		pessoaService.save(pessoa);
		pessoa.setEndereco(endereco2);

		when(pessoaRepository.save(pessoa)).thenReturn(pessoa);
		when(pessoaRepository.findById(cpf)).thenReturn(java.util.Optional.of(pessoa));
		assertEquals(pessoa.getEndereco().getCep(), pessoaService.update(pessoa).getEndereco().getCep() );
		assertEquals(cep, pessoaService.update(pessoa).getEndereco().getCep() );
		assertNotEquals("04520-013", pessoaService.update(pessoa).getEndereco().getCep() );
	}

	@Test
	public void whenExceptionThrown_thenAssertionSucceeds() {
		assertThrows(PessoaNaoExisteException.class, this::geraExcecao);
	}

	public void geraExcecao() throws Exception {
		Endereco endereco1= new Endereco();
		Endereco endereco2= new Endereco();

		Pessoa pessoa = new Pessoa(999, "Pranya", "Udzkha", endereco2);
		when(pessoaRepository.save(pessoa)).thenReturn(pessoa);
		assertEquals(pessoa, pessoaService.update(pessoa));
		assertThrows(NullPointerException.class,  () -> { Pessoa pessoa1= new Pessoa(999, "Pranya", "Udzkha", endereco2); pessoaRepository.save(pessoa); } );
	}

	/*
	TODO: not implemented yet.
	@Test
	public void deletePessoaTest() {
		Pessoa pessoa= new Pessoa(999, "", "Pune", endereco);
		pessoaService.deletePessoa(pessoa);
		verify(pessoaRepository, times(1)).delete(user);
	}
	*/

	/*
	public String consultaEnderecoPorCEP(String cep) {
	public String consultaEstados() throws IOException, JSONException {
	public String consultaMunicipoPorEstados(String estado) throws JSONException, IOException
	*/

}



















