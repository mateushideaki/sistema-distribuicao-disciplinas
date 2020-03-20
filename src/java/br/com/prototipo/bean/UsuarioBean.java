/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.prototipo.bean;

import br.com.prototipo.dao.ProfessorDao;
import br.com.prototipo.dao.UsuarioDao;
import br.com.prototipo.entity.Professor;
import br.com.prototipo.entity.Usuario;
import java.io.IOException;
import java.io.Serializable;
import java.util.Objects;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Mateus
 */
@ManagedBean
@SessionScoped
public class UsuarioBean implements Serializable {

    /**
     * Creates a new instance of UsuarioBean
     */
    private Usuario usuario = new Usuario();
    private Usuario usuarioCadastro = new Usuario();
    private String registro;
    private UsuarioDao usuarioDao = new UsuarioDao();
    private String mensagem = null;
    private String mensagemLogin;

    public UsuarioBean() {
    }

    public String addUsuarioProfessor() {
        ProfessorDao pd = new ProfessorDao();
        Professor p = pd.getProfessor(registro);
        if (p.getCargoAdm()) {
            this.usuarioCadastro.setGestor(true);
        }
        String escolha = usuarioDao.addUSuarioProfessor(usuarioCadastro, registro);
        switch (escolha) {
            case "sucesso":
                this.setMensagem("Usuário cadastrado com sucesso.");
                break;
            case "jaCadastrado":
                this.setMensagem("Erro: usuário já cadastrado.");
                break;
            case "naoEncontrado":
                this.setMensagem("Erro: registro não encontrado no banco de dados.");
                break;
            
        }
        usuarioCadastro.setId(0);
        usuarioCadastro.setLogin(null);
        usuarioCadastro.setSenha(null);
        usuarioCadastro.setGestor(false);

        return "cadastrarUsuarioProfessor";
    }

    public String verificaLogin() throws IOException {
        try {
            if (usuarioDao.validarLogin2(this.usuario) != null) {
                this.usuario = usuarioDao.validarLogin2(this.usuario);
                this.registro = usuario.getRegistro();
                if (usuario.isGestor()) {
                    return "menuPrincipal";
                } else {
                    return "menuProfessor";
                }
            } else {
                this.mensagemLogin = "Aviso: Login ou senha inválidos, caso não seja cadastrado clique em 'Cadastrar usuario'.";
                return "index";
            }
            //Escritor esc = new Escritor();
            //esc.escreve();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "index";
    }

    public boolean verificaSessao() {
        if (usuarioDao.validarLogin2(this.usuario) != null) {
            usuario = usuarioDao.validarLogin2(this.usuario);
            return usuario.isGestor();
        } else {
            return false;
        }
    }

    public boolean sessaoExpirada() {
        if (usuarioDao.validarLogin2(this.usuario) == null) {
            return true;
        } else {
            usuario = usuarioDao.validarLogin2(this.usuario);
            return !usuario.isGestor();
        }
    }

    public boolean verificaSessaoProfessor() {
        return usuarioDao.validarLogin2(this.usuario) != null;
    }

    public boolean sessaoExpiradaProfessor() {
        return usuarioDao.validarLogin2(this.usuario) == null;
    }

    public boolean verificaSessaoSomenteProfessor() {
        return (verificaSessaoProfessor() && !verificaSessao());
    }

    public String encerrarSessao() {
        usuario = new Usuario();
        this.registro = null;
        return "index";
    }

    public void apagaMensagem() {
        this.mensagem = null;
    }

    public void apagaMensagem2() {
        this.mensagemLogin = null;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Usuario getUsuarioCadastro() {
        return usuarioCadastro;
    }

    public void setUsuarioCadastro(Usuario usuarioCadastro) {
        this.usuarioCadastro = usuarioCadastro;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getRegistro() {
        return registro;
    }

    public void setRegistro(String registro) {
        this.registro = registro;
    }

    public String getMensagemLogin() {
        return mensagemLogin;
    }

    public void setMensagemLogin(String mensagemLogin) {
        this.mensagemLogin = mensagemLogin;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 41 * hash + Objects.hashCode(this.usuario);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final UsuarioBean other = (UsuarioBean) obj;
        if (!Objects.equals(this.usuario, other.usuario)) {
            return false;
        }
        return true;
    }

}
