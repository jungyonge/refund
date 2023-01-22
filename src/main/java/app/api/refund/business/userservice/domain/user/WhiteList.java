package app.api.refund.business.userservice.domain.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table
@Getter
@NoArgsConstructor
public class WhiteList {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String regNo;

    private WhiteList(String name, String regNo) {
        this.setName(name);
        this.setRegNo(regNo);
    }

    public static WhiteList create(String name, String regNo) {
        return new WhiteList(name, regNo);
    }

    private void setName(String name) {
        this.name = name;
    }

    private void setRegNo(String regNo) {
        this.regNo = regNo;
    }
}
