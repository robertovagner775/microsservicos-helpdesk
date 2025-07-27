package com.roberto.suporteTecnico.model;

import com.roberto.suporteTecnico.dto.requests.ClientRequestDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "CLIENT")
public class Client {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

	@OneToOne
	@JoinColumn(name = "username")
	private Users user;

    private String telephone;

    public static Client createClient(ClientRequestDTO clientRequest, Users user) {
        return new Client(null, clientRequest.name(), user, clientRequest.telephone());
    }



    
    
}
