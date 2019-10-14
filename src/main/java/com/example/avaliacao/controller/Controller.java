package com.example.avaliacao.controller;

import com.example.avaliacao.domain.Account;
import com.example.avaliacao.domain.Employee;
import com.example.avaliacao.domain.Endereco;
import com.example.avaliacao.domain.Pessoa;
import com.example.avaliacao.repositories.EmployeeRepository;
import com.example.avaliacao.service.EmployeeService;
import com.example.avaliacao.service.ExternalService;
import com.example.avaliacao.service.PessoaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Component
@Slf4j
public class Controller {

	@Autowired
	private PessoaService pessoaService;

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private ExternalService externalService;

	private Account account=null;

	@PostMapping(path = "/employee/addEmployee" , produces = MediaType.APPLICATION_JSON_VALUE)
	String addEmployee(@RequestBody Employee employee) throws Exception {
		log.info(String.format(Locale.getDefault(), "Controller:addEmployee: id: %s", employee.getId()));
		Employee employeeDB= employeeService.save(employee);
		return String.valueOf(employeeDB.getId());
	}

	@GetMapping(path = "/employee/listAll")
	String listEmployees() throws JSONException {
		log.info(String.format(Locale.getDefault(), "Controller:listEmployees: %s", "<>"));
		JSONObject response= new JSONObject();
		List<Employee> result = employeeService.findAll();
		if (result.isEmpty()) {
			log.info(String.format(Locale.getDefault(), "Controller:listEmployees: %s", "EMPTY"));
		}
		for (Employee employee: result) {
			log.info(String.format(Locale.getDefault(), "Controller:employee.getId(): %s", employee.getId()));
			log.info(String.format(Locale.getDefault(), "Controller:employee.getName(): %s", employee.getName()));
			response.put(employee.getId().toString(), employee.getName());
		}
		return response.toString();
	}

	@PostMapping(path = "/clientes/novoCliente" , produces = MediaType.APPLICATION_JSON_VALUE)
	String criarCliente(@RequestBody Pessoa pessoaJSON) throws Exception {
		Pessoa pessoa= new Pessoa(pessoaJSON);
		log.info(String.format(Locale.getDefault(), "Controller:novoCliente: id: %s", pessoa.getCpf()));
		log.info(String.format(Locale.getDefault(), "Controller:novoCliente: nome: %s", pessoa.getNome ()));
		log.info(String.format(Locale.getDefault(), "Controller:novoCliente: sobrenome: %s", pessoa.getSobrenome()));
		log.info(String.format(Locale.getDefault(), "Controller:novoCliente: cpf: %s", pessoa.getCpf()));
		Pessoa pessoaDB= pessoaService.save(pessoa);
		return String.valueOf(pessoaDB.getCpf());
	}

	@GetMapping(path = "/clientes/list/{cpf}")
	String ListCliente(@PathVariable Long cpf) throws JSONException {
		JSONObject response = new JSONObject();
		Optional<Pessoa> result = pessoaService.findById(cpf);
		if (result.isPresent()) {
			Pessoa pessoa= result.get();
			log.info(String.format(Locale.getDefault(), "Controller:ListCliente: pessoa.getCpf(): %s", pessoa.getCpf()));
			log.info(String.format(Locale.getDefault(), "Controller:ListCliente: pessoa.getNome(): %s", pessoa.getNome()));
			response.put(pessoa.getCpf().toString(), pessoa.getNome());
		}
		return response.toString();
	}

	@PutMapping(path = "/clientes/alteraCliente/{cpf}" , produces = MediaType.APPLICATION_JSON_VALUE)
	String alteraCliente(@RequestBody Pessoa pessoaJSON, @PathVariable Long cpf) throws Exception {
		String result="";
		Pessoa pessoa= new Pessoa(pessoaJSON);
		if (cpf.compareTo(pessoa.getCpf()) == 0) {
			log.info(String.format(Locale.getDefault(), "Controller:alteraCliente: id: %s", pessoa.getCpf()));
			log.info(String.format(Locale.getDefault(), "Controller:alteraCliente: nome: %s", pessoa.getNome()));
			log.info(String.format(Locale.getDefault(), "Controller:alteraCliente: sobrenome: %s", pessoa.getSobrenome()));
			log.info(String.format(Locale.getDefault(), "Controller:alteraCliente: cpf: %s", pessoa.getCpf()));
			Pessoa pessoaDB = pessoaService.update(pessoa);
			result= String.valueOf(pessoaDB.getCpf());
		} else {
		    result= "cpf nao confere";
		}
		return result;
	}

	@GetMapping(path = "/clientes/listAll")
	String ListAllClientes() {
		JSONObject response = new JSONObject();
		List<Pessoa> result = pessoaService.findAll();
		for (Pessoa pessoa : result) {
			try {
				log.info(String.format(Locale.getDefault(), "Controller:pessoa.getCpf(): %s", pessoa.getCpf()));
				log.info(String.format(Locale.getDefault(), "Controller:pessoa.getNome(): %s", pessoa.getNome()));
				response.put(pessoa.getCpf().toString(), pessoa.getNome());
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return response.toString();
	}

	@GetMapping(path = "/servicos/consultaEndereco/{cep}")
	String consultaEnderecoPorCEP(@PathVariable Long cep) throws Exception {
		return externalService.consultaEnderecoPorCEP(String.valueOf(cep));
	}

	@GetMapping(path = "/servicos/consultaEstados")
	String consultaEstados() throws Exception {
		return externalService.consultaEstados();
	}

	@GetMapping(path = "/servicos/consultaMunicipios/{estado}")
	String consultaMunicipios(@PathVariable String estado) throws Exception {
		return externalService.consultaMunicipoPorEstados(estado);
	}

	@GetMapping(path = "/contas/newAccount")
	String newRandomAccount() {
		this.account= Account.gerarNovaConta();
		log.info(String.format(Locale.getDefault(), "Controller:newAccount: %s", account.toString()));
		return String.valueOf(account.getAccountID());
	}

	@PostMapping(path = "/contas/newAccount" , produces = MediaType.APPLICATION_JSON_VALUE)
	String newAccount(@RequestBody Pessoa pessoaJSON) {
		log.info(String.format(Locale.getDefault(), "Controller:newAccount: pessoaJSON: %s", pessoaJSON.toString()));
		//Pessoa pessoa= parsePessoa(pessoaJSON);
		this.account= Account.gerarNovaConta(pessoaJSON);
		log.info(String.format(Locale.getDefault(), "Controller:newAccount: %s", account.getPessoa().getNome()));
		return String.valueOf(account.getAccountID());
	}

	@PostMapping(path = "/contas/alterarEndereco/{targetAccountId}", produces = MediaType.APPLICATION_JSON_VALUE)
	String alterarEndereco(@PathVariable("targetAccountId") long targetAccountId, @RequestBody Endereco enderecoJSON) throws Exception {
		log.info(String.format(Locale.getDefault(), "Controller:alterarEndereco: enderecoJSON: %s", enderecoJSON.toString()));
		log.info(String.format(Locale.getDefault(), "Controller:alterarEndereco: %s", targetAccountId ));
		JSONObject result= new JSONObject();
		result.put("resultado", false);
		return result.toString();
	}

}
