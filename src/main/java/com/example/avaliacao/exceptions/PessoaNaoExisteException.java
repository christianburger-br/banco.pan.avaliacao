package com.example.avaliacao.exceptions;

public class PessoaNaoExisteException extends RuntimeException {
    private String exceptionMsg= "Pessoa nao Existe, utilize: /clientes/novoCliente para criar";

    public PessoaNaoExisteException(String exceptionMsg) {
        this.exceptionMsg+= ": " + exceptionMsg  ;
    }

    public String getExceptionMsg(){
        return this.exceptionMsg;
    }

    public void setExceptionMsg(String exceptionMsg) {
        this.exceptionMsg = exceptionMsg;
    }
}
