package app.api.refund.business.userservice.domain.user;


import app.api.refund.business.userservice.domain.UserDomainValidationMessage;
import app.api.refund.business.userservice.domain.role.Role;
import app.api.refund.domain.DomainValidationException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "`user`")
@Getter
@NoArgsConstructor
public class User {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String userId;

    private String password;

    private String name;

    private String regNo;

    private String regNoAes256;

    private String regNoBcrypt;

    private boolean activated;

    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;

    @ManyToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinTable(name = "user_authority", joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private List<Role> roles = new ArrayList<>();

    private LocalDateTime created;

    private LocalDateTime updated;

    private LocalDateTime lastLoggedIn;

    public void addRole(Role role) {
        if (this.roles.stream().anyMatch(r -> r.getId() == role.getId())) {
            throw new DomainValidationException(UserDomainValidationMessage.ROLE_ALREADY_EXIST);
        }

        this.roles.add(role);
    }

    public void login() {
        this.setLastLoggedIn(LocalDateTime.now());
    }

    public User(String userId, String password, String name, String regNo) {

        this.setUserId(userId);
        this.setPassword(password);
        this.setName(name);
        this.setRegNo(regNo);
        this.setActivated(true);
        this.setUserStatus(UserStatus.JOINED);
        LocalDateTime now = LocalDateTime.now();
        this.setCreated(now);
    }

    public static User create(String userId, String password, String name, String regNo) {
        return new User(userId, password, name, regNo);
    }

    private void setPassword(String password) {
        this.password = password;
    }

    private void setUserId(String userId) {
        this.userId = userId;
    }

    private void setName(String name) {
        this.name = name;
    }

    private void setRegNo(String regNo) {
        this.regNo = regNo;
    }

    private void setCreated(LocalDateTime created) {
        this.created = created;
    }

    private void setUpdated(LocalDateTime updated) {
        this.updated = updated;
    }

    private void setLastLoggedIn(LocalDateTime lastLoggedIn) {
        this.lastLoggedIn = lastLoggedIn;
    }


    private void setActivated(boolean activated) {
        this.activated = activated;
    }

    private void setUserStatus(UserStatus userStatus) {
        this.userStatus = userStatus;
    }

}
