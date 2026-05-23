import java.util.Date;

public class Case {
    private int caseId;
    private String caseNumber;
    private String caseName;
    private String caseType;
    private String description;
    private int clientId;
    private int primaryLawyerId;
    private String coCounselIds;
    private String courtName;
    private String jurisdiction;
    private Date filedDate;
    private String status;
    private String feeType;
    private double hourlyRate;
    private String opposingParty;
    private Date createdAt;
    private Date updatedAt;

    // Constructors
    public Case() {}

    // Getters and Setters
    public int getCaseId() { return caseId; }
    public void setCaseId(int caseId) { this.caseId = caseId; }

    public String getCaseNumber() { return caseNumber; }
    public void setCaseNumber(String caseNumber) { this.caseNumber = caseNumber; }

    public String getCaseName() { return caseName; }
    public void setCaseName(String caseName) { this.caseName = caseName; }

    public String getCaseType() { return caseType; }
    public void setCaseType(String caseType) { this.caseType = caseType; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public int getClientId() { return clientId; }
    public void setClientId(int clientId) { this.clientId = clientId; }

    public int getPrimaryLawyerId() { return primaryLawyerId; }
    public void setPrimaryLawyerId(int primaryLawyerId) { this.primaryLawyerId = primaryLawyerId; }

    public String getCoCounselIds() { return coCounselIds; }
    public void setCoCounselIds(String coCounselIds) { this.coCounselIds = coCounselIds; }

    public String getCourtName() { return courtName; }
    public void setCourtName(String courtName) { this.courtName = courtName; }

    public String getJurisdiction() { return jurisdiction; }
    public void setJurisdiction(String jurisdiction) { this.jurisdiction = jurisdiction; }

    public Date getFiledDate() { return filedDate; }
    public void setFiledDate(Date filedDate) { this.filedDate = filedDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getFeeType() { return feeType; }
    public void setFeeType(String feeType) { this.feeType = feeType; }

    public double getHourlyRate() { return hourlyRate; }
    public void setHourlyRate(double hourlyRate) { this.hourlyRate = hourlyRate; }

    public String getOpposingParty() { return opposingParty; }
    public void setOpposingParty(String opposingParty) { this.opposingParty = opposingParty; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }

    public Date getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }

    @Override
    public String toString() {
        return "Case{" +
                "caseId=" + caseId +
                ", caseNumber='" + caseNumber + '\'' +
                ", caseName='" + caseName + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}