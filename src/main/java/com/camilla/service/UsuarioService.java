package com.camilla.service;

import com.camilla.domain.Usuario;
import com.camilla.repository.UsuarioRepository;
import com.camilla.service.exception.UserNaoEncontradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    public Usuario findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new UserNaoEncontradoException(id));
    }

    public List<Usuario> todos() {
        return repository.findAll();
    }

    public Usuario save(Usuario usuario) {
        return repository.save(usuario);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public Usuario update(Long id, Usuario usuario) {
        usuario.setId(id);
        return repository.save(usuario);
    }

}
