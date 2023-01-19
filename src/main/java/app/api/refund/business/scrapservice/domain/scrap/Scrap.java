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
public class Scrap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private Long userId;

    private String appVer;

    private String errMsg;

    private String company;

    private String svcCd;

    private String hostNm;

    private LocalDateTime workerResDt;

    private LocalDateTime workerReqDt;

    private LocalDateTime created;

    private Scrap(Long userId, String appVer, String errMsg, String company, String svcCd,
            String hostNm, LocalDateTime workerResDt, LocalDateTime workerReqDt) {
        this.setUserId(userId);
        this.setErrMsg(errMsg);
        this.setAppVer(appVer);
        this.setCompany(company);
        this.setSvcCd(svcCd);
        this.setHostNm(hostNm);
        this.setWorkerReqDt(workerReqDt);
        this.setWorkerResDt(workerResDt);
        this.setCreated(LocalDateTime.now());
    }

    public static Scrap create(Long userId, String appVer, String errMsg, String company,
            String svcCd, String hostNm, LocalDateTime workerResDt, LocalDateTime workerReqDt) {
        return new Scrap(userId, appVer, errMsg, company, svcCd, hostNm, workerResDt, workerReqDt);
    }

    private void setUserId(Long userId) {
        this.userId = userId;
    }

    private void setAppVer(String appVer) {
        this.appVer = appVer;
    }

    private void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    private void setCompany(String company) {
        this.company = company;
    }

    private void setSvcCd(String svcCd) {
        this.svcCd = svcCd;
    }

    private void setHostNm(String hostNm) {
        this.hostNm = hostNm;
    }

    private void setWorkerResDt(LocalDateTime workerResDt) {
        this.workerResDt = workerResDt;
    }

    private void setWorkerReqDt(LocalDateTime workerReqDt) {
        this.workerReqDt = workerReqDt;
    }

    private void setCreated(LocalDateTime created) {
        this.created = created;
    }
}
