package com.roberto.ticket.exceptions;

public class NotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private String message;
	private String idEntity;


	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getIdEntity() {
		return idEntity;
	}

	public void setIdEntity(String idEntity) {
		this.idEntity = idEntity;
	}

	public NotFoundException(String mensagem, String idEntity) {
		super(mensagem);
		this.message = mensagem;
		this.idEntity = idEntity;
	}

	public NotFoundException() {
		super("Recurso nao foi encontrado!!");
	}

}
