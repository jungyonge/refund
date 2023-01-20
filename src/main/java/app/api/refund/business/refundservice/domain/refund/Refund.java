package app.api.refund.business.refundservice.domain.refund;

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
public class Refund {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private Long userId;

    @Comment("결정세액")
    private Integer determinedTaxAmount;

    @Comment("퇴직연금세액공제")
    private Integer retirementPensionTaxCredit;

    private LocalDateTime created;

    private LocalDateTime updated;

    private Refund(Long userId, Integer determinedTaxAmount, Integer retirementPensionTaxCredit) {
        this.setUserId(userId);
        this.setDeterminedTaxAmount(determinedTaxAmount);
        this.setRetirementPensionTaxCredit(retirementPensionTaxCredit);
        this.setCreated(LocalDateTime.now());
    }

    public static Refund create(Long userId, Integer determinedTaxAmount,
            Integer retirementPensionTaxCredit) {
        return new Refund(userId, determinedTaxAmount, retirementPensionTaxCredit);
    }

    private void setUserId(Long userId) {
        this.userId = userId;
    }

    private void setDeterminedTaxAmount(Integer determinedTaxAmount) {
        this.determinedTaxAmount = determinedTaxAmount;
    }

    private void setRetirementPensionTaxCredit(Integer retirementPensionTaxCredit) {
        this.retirementPensionTaxCredit = retirementPensionTaxCredit;
    }

    private void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public void setUpdated(LocalDateTime updated) {
        this.updated = updated;
    }
}
