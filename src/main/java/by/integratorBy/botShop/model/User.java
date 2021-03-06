package by.integratorBy.botShop.model;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Builder
@Table(name = "user")
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", nullable = false)
    private Integer id;

    @Column(name = "ChatId", nullable = false)
    private Long chatId;

    @Column(name = "Firstname")
    private String firstname;

    @Column(name = "Lastname")
    private String lastname;

    @Column(name = "Username")
    private String username;

    @Column(name = "BotLastMessageId")
    private Integer botLastMessageId;

    @Column(name = "BotLastMessageDate")
    private Integer botLastMessageDate;

    @Column(name = "BotLastMessageEditable", columnDefinition = "TINYINT(1)")
    private Boolean botLastMessageEditable;

//    @Column(name = "Banned", columnDefinition = "TINYINT(1)", nullable = false)
//    private Boolean banned;

    @Column(name = "CurrentPage", nullable = false)
    private Integer currentPage;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinTable( name = "user_client",
            joinColumns = {@JoinColumn(name = "UserId", referencedColumnName = "Id")},
            inverseJoinColumns = {@JoinColumn(name = "ClientId", referencedColumnName = "Id")})
    private Client client;

    public Boolean hasLastBotMessage() {
        return botLastMessageId != null;
    }
}
