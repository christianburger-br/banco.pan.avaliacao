package com.example.avaliacao.exceptions;

public class PessoaJaExisteException extends RuntimeException {
    private String exceptionMsg= "Pessoa Ja Existe, utilize: PUT >> /clientes/alteraCliente/{cpf} para alterar.";

    public PessoaJaExisteException(String exceptionMsg) {
        this.exceptionMsg+= ": " + exceptionMsg  ;
    }

    public String getExceptionMsg(){
        return this.exceptionMsg;
    }

    public void setExceptionMsg(String exceptionMsg) {
        this.exceptionMsg = exceptionMsg;
    }
}
