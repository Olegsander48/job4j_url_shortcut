package ru.job4j.shortcut.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.job4j.shortcut.validation.Operation;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "links")
public class Link {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "Site url can not be null or empty", groups = {Operation.OnConversion.class})
    private String url;
    private String code;
    private int total;

    @ManyToOne
    @JoinColumn(name = "site_id")
    private Site site;
}
