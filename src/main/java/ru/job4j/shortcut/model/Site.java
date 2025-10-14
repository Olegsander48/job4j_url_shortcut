package ru.job4j.shortcut.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.job4j.shortcut.validation.Operation;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "sites")
public class Site {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JsonProperty("site")
    @Column(name = "domain_name")
    @NotBlank(message = "Site domain can not be null or empty", groups = {Operation.OnRegistration.class})
    private String domainName;
    private String login;
    private String password;
}
