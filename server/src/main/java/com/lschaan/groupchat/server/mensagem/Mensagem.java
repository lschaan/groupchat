package com.lschaan.groupchat.server.mensagem;

public class Mensagem {
  private Usuario remetente;
  private String mensagem;
  private TipoMensagem tipo;
  private String complemento;

  public Mensagem(Usuario remetente, String mensagem, TipoMensagem tipo, String complemento) {
    this.remetente = remetente;
    this.mensagem = mensagem;
    this.tipo = tipo;
    this.complemento = complemento;
  }

  public Mensagem(Usuario remetente, String mensagem, TipoMensagem tipo) {
    this.remetente = remetente;
    this.mensagem = mensagem;
    this.tipo = tipo;
  }

  public Mensagem() {}

  public Usuario getRemetente() {
    return remetente;
  }

  public void setRemetente(Usuario remetente) {
    this.remetente = remetente;
  }

  public String getMensagem() {
    return mensagem;
  }

  public void setMensagem(String mensagem) {
    this.mensagem = mensagem;
  }

  public TipoMensagem getTipo() {
    return tipo;
  }

  public void setTipo(TipoMensagem tipo) {
    this.tipo = tipo;
  }

  public String getComplemento() {
    return complemento;
  }

  public void setComplemento(String complemento) {
    this.complemento = complemento;
  }
}
