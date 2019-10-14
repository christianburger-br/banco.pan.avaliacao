package com.example.avaliacao.service;

import com.example.avaliacao.domain.Pessoa;
import com.example.avaliacao.exceptions.PessoaJaExisteException;
import com.example.avaliacao.exceptions.PessoaNaoExisteException;
import com.example.avaliacao.repositories.PessoaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
@Slf4j
public class PessoaService {

    @Autowired
    private PessoaRepository pessoaRepository;

    PessoaService(PessoaRepository pessoaRepository) {
        this.pessoaRepository= pessoaRepository;
    }

    public Optional<Pessoa> findById( long id) {
        return pessoaRepository.findById(id);
    }

    public List<Pessoa> findAll() {
            return (List<Pessoa>) pessoaRepository.findAll();
    }

    public Pessoa update(Pessoa pessoa) throws Exception {
        if (!pessoaRepository.findById(pessoa.getCpf()).isPresent()) {
            throw new PessoaNaoExisteException("cpf: " + pessoa.getCpf());
        } else {
            log.info(String.format(Locale.getDefault(), "save: pessoa.getCpf: %d", pessoa.getCpf()));
            log.info(String.format(Locale.getDefault(), "save: pessoaRepository==null: %b", pessoaRepository == null));

            Pessoa pessoa1 = pessoaRepository.save(pessoa);
            log.info(String.format(Locale.getDefault(), "save: pessoa1==null: %b", pessoa1 == null));
            return pessoa1;
        }
    }

    public Pessoa save(Pessoa pessoa) throws Exception {
        if (pessoaRepository.findById(pessoa.getCpf()).isPresent()) {
            throw new PessoaJaExisteException("cpf: " + pessoa.getCpf());
        } else {
            log.info(String.format(Locale.getDefault(), "save: pessoa.getCpf: %d", pessoa.getCpf()));
            log.info(String.format(Locale.getDefault(), "save: pessoaRepository==null: %b", pessoaRepository == null));

            Pessoa pessoa1 = pessoaRepository.save(pessoa);
            log.info(String.format(Locale.getDefault(), "save: pessoa1==null: %b", pessoa1 == null));
            return pessoa1;
        }
    }
}
