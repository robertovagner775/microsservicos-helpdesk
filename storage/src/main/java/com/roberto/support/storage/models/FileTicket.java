package com.roberto.support.storage.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity(name = "file_ticket")
public class FileTicket {

    @Id
    private Integer idTicket;

    @OneToMany(mappedBy = "fileTicket", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<Archive> files = new ArrayList<Archive>();

}
