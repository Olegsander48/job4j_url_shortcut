package ru.job4j.shortcut.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "sites")
public class Site {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "domain_name")
    private String domainName;
    private String login;
    private String password;

    @OneToMany
    @JoinColumn(name = "site_id")
    List<Link> links;
}
