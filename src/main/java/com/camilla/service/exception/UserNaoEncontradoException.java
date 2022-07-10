package com.camilla.service.exception;

public class UserNaoEncontradoException extends RuntimeException {
    public UserNaoEncontradoException(Long id) {
        super("Usuario não encontrado.!");
    }
}
