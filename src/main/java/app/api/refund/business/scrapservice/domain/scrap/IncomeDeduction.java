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
    private Double premium;

    @Comment("교육비")
    private Double educationExpenses;

    @Comment("기부금")
    private Double donations;

    @Comment("의료비")
    private Double medicalExpenses;

    @Comment("퇴직연금")
    private Double retirementPension;

    private LocalDateTime created;

    private IncomeDeduction(Long userId, Long scrapId, Double premium, Double educationExpenses,
            Double donations, Double medicalExpenses, Double retirementPension) {
        this.setUserId(userId);
        this.setScrapId(scrapId);
        this.setPremium(premium);
        this.setEducationExpenses(educationExpenses);
        this.setDonations(donations);
        this.setMedicalExpenses(medicalExpenses);
        this.setRetirementPension(retirementPension);
        this.setCreated(LocalDateTime.now());
    }

    public static IncomeDeduction create(Long userId, Long scrapId, Double premium,
            Double educationExpenses, Double donations, Double medicalExpenses,
            Double retirementPension) {
        return new IncomeDeduction(userId, scrapId, premium, educationExpenses, donations,
                medicalExpenses, retirementPension);
    }


    private void setUserId(Long userId) {
        this.userId = userId;
    }

    private void setScrapId(Long scrapId) {
        this.scrapId = scrapId;
    }

    private void setPremium(Double premium) {
        this.premium = premium;
    }

    private void setEducationExpenses(Double educationExpenses) {
        this.educationExpenses = educationExpenses;
    }

    private void setDonations(Double donations) {
        this.donations = donations;
    }

    private void setMedicalExpenses(Double medicalExpenses) {
        this.medicalExpenses = medicalExpenses;
    }

    private void setRetirementPension(Double retirementPension) {
        this.retirementPension = retirementPension;
    }

    private void setCreated(LocalDateTime created) {
        this.created = created;
    }
}
