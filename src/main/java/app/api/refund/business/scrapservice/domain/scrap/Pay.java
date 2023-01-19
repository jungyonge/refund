package app.api.refund.business.scrapservice.domain.scrap;

import java.time.LocalDate;
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
public class Pay {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private Long userId;

    private Long scrapId;

    @Comment("소득내역")
    private String incomeDetails;

    @Comment("총지급액")
    private Integer totalPayment;

    @Comment("업무시작일")
    private LocalDate workStartDate;

    @Comment("기업명")
    private String company;

    @Comment("이름")
    private String name;

    @Comment("지급일")
    private LocalDate paymentDate;

    @Comment("업무종료일")
    private LocalDate workEndDate;

    @Comment("소득구분")
    private String incomeClassification;

    @Comment("사업자등록번호")
    private String companyRegistrationNumber;

    private LocalDateTime created;

    private Pay(Long userId, Long scrapId, String incomeDetails, Integer totalPayment,
            LocalDate workStartDate, LocalDate workEndDate, String company, String name,
            LocalDate paymentDate, String incomeClassification, String companyRegistrationNumber) {
        this.setUserId(userId);
        this.setScrapId(scrapId);
        this.setIncomeClassification(incomeClassification);
        this.setTotalPayment(totalPayment);
        this.setWorkStartDate(workStartDate);
        this.setWorkEndDate(workEndDate);
        this.setCompany(company);
        this.setName(name);
        this.setPaymentDate(paymentDate);
        this.setIncomeDetails(incomeDetails);
        this.setCompanyRegistrationNumber(companyRegistrationNumber);
        this.setCreated(LocalDateTime.now());
    }

    public static Pay create(Long userId, Long scrapId, String incomeDetails, Integer totalPayment,
            LocalDate workStartDate, LocalDate workEndDate, String company, String name,
            LocalDate paymentDate, String incomeClassification, String companyRegistrationNumber) {
        return new Pay(userId, scrapId, incomeDetails, totalPayment, workStartDate, workEndDate,
                company, name, paymentDate, incomeClassification, companyRegistrationNumber);
    }

    private void setUserId(Long userId) {
        this.userId = userId;
    }

    private void setScrapId(Long scrapId) {
        this.scrapId = scrapId;
    }

    private void setIncomeDetails(String incomeDetails) {
        this.incomeDetails = incomeDetails;
    }

    private void setTotalPayment(Integer totalPayment) {
        this.totalPayment = totalPayment;
    }

    private void setWorkStartDate(LocalDate workStartDate) {
        this.workStartDate = workStartDate;
    }

    private void setCompany(String company) {
        this.company = company;
    }

    private void setName(String name) {
        this.name = name;
    }

    private void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    private void setWorkEndDate(LocalDate workEndDate) {
        this.workEndDate = workEndDate;
    }

    private void setIncomeClassification(String incomeClassification) {
        this.incomeClassification = incomeClassification;
    }

    private void setCompanyRegistrationNumber(String companyRegistrationNumber) {
        this.companyRegistrationNumber = companyRegistrationNumber;
    }

    private void setCreated(LocalDateTime created) {
        this.created = created;
    }
}
