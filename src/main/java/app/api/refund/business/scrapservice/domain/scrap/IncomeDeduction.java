package app.api.refund.business.scrapservice.domain.scrap;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Entity
@Getter
@NoArgsConstructor
public class IncomeDeduction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private Long userId;

    private Long scrapId;

    @Comment("보험료")
    private Integer premium;

    @Comment("교육비")
    private Integer educationExpenses;

    @Comment("기부금")
    private Integer donations;

    @Comment("의료비")
    private Integer medicalExpenses;

    @Comment("퇴직연금")
    private Integer retirementPension;

    private LocalDateTime created;

    private IncomeDeduction(Long userId, Long scrapId, Integer premium, Integer educationExpenses,
            Integer donations, Integer medicalExpenses, Integer retirementPension) {
        this.setUserId(userId);
        this.setScrapId(scrapId);
        this.setPremium(premium);
        this.setEducationExpenses(educationExpenses);
        this.setDonations(donations);
        this.setMedicalExpenses(medicalExpenses);
        this.setRetirementPension(retirementPension);
        this.setCreated(LocalDateTime.now());
    }

    public static IncomeDeduction create(Long userId, Long scrapId, Integer premium,
            Integer educationExpenses, Integer donations, Integer medicalExpenses,
            Integer retirementPension) {
        return new IncomeDeduction(userId, scrapId, premium, educationExpenses, donations,
                medicalExpenses, retirementPension);
    }


    private void setUserId(Long userId) {
        this.userId = userId;
    }

    private void setScrapId(Long scrapId) {
        this.scrapId = scrapId;
    }

    private void setPremium(Integer premium) {
        this.premium = premium;
    }

    private void setEducationExpenses(Integer educationExpenses) {
        this.educationExpenses = educationExpenses;
    }

    private void setDonations(Integer donations) {
        this.donations = donations;
    }

    private void setMedicalExpenses(Integer medicalExpenses) {
        this.medicalExpenses = medicalExpenses;
    }

    private void setRetirementPension(Integer retirementPension) {
        this.retirementPension = retirementPension;
    }

    private void setCreated(LocalDateTime created) {
        this.created = created;
    }
}
