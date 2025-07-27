package com.roberto.suporteTecnico.exceptions;

public class NotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private String message;
	private Integer idEntity;


	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Integer getIdEntity() {
		return idEntity;
	}

	public void setIdEntity(Integer idEntity) {
		this.idEntity = idEntity;
	}

	public NotFoundException(String mensagem, Integer idEntity) {
		super(mensagem);
		this.message = mensagem;
		this.idEntity = idEntity;
	}

	public NotFoundException() {
		super("Recurso nao foi encontrado!!");
	}

}
