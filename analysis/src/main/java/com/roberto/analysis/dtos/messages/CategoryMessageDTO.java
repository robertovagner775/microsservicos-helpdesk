package com.roberto.analysis.dtos.messages;

import java.io.Serializable;

public record CategoryMessageDTO(String id, String title, String description) implements Serializable {
}
