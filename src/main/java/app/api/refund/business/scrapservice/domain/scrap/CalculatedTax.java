package app.api.refund.business.scrapservice.domain.scrap;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class CalculatedTax {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private Long userId;

    private Long scrapId;

    private Double calculatedTaxAmount;

    private LocalDateTime created;

    private void setUserId(Long userId) {
        this.userId = userId;
    }

    private void setScrapId(Long scrapId) {
        this.scrapId = scrapId;
    }

    private void setCreated(LocalDateTime created) {
        this.created = created;
    }

    private void setCalculatedTaxAmount(Double calculatedTaxAmount) {
        this.calculatedTaxAmount = calculatedTaxAmount;
    }

    private CalculatedTax(Long userId, Long scrapId, Double calculatedTaxAmount) {
        this.setUserId(userId);
        this.setScrapId(scrapId);
        this.setCalculatedTaxAmount(calculatedTaxAmount);
        this.setCreated(LocalDateTime.now());
    }

    public static CalculatedTax create(Long userId, Long scrapId, Double calculatedTaxAmount) {

        return new CalculatedTax(userId, scrapId, calculatedTaxAmount);
    }


}
