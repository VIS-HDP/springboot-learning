package com.robingao.mongodemo;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "new_policy_pdfs")
public class NewPolicyPdfs {

    private String policyType;
    private String policyTitle;
    private String policyDate;
    private String policyInfo ;
    private List<String> policyTags;

    public String getPolicyType() {
        return policyType;
    }

    public void setPolicyType(String policyType) {
        this.policyType = policyType;
    }

    public String getPolicyTitle() {
        return policyTitle;
    }

    public void setPolicyTitle(String policyTitle) {
        this.policyTitle = policyTitle;
    }

    public String getPolicyDate() {
        return policyDate;
    }

    public void setPolicyDate(String policyDate) {
        this.policyDate = policyDate;
    }

    public String getPolicyInfo() {
        return policyInfo;
    }

    public void setPolicyInfo(String policyInfo) {
        this.policyInfo = policyInfo;
    }

    public List<String> getPolicyTags() {
        return policyTags;
    }

    public void setPolicyTags(List<String> policyTags) {
        this.policyTags = policyTags;
    }





}
