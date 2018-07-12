package com.vis.demo.jta.vo;

import java.util.UUID;

public class JournalVO {
    private String journal_id;
    private String journal_code;
    private String journal_name;
    private Integer journal_type;

    public JournalVO() {
        super();
        this.journal_id = UUID.randomUUID().toString();
    }
    public JournalVO(String journal_id, String journal_code, String journal_name, Integer journal_type) {
        super();
        this.journal_id = journal_id;
        this.journal_code = journal_code;
        this.journal_name = journal_name;
        this.journal_type = journal_type;
    }

    public String getJournal_id() {
        return journal_id;
    }

    public void setJournal_id(String journal_id) {
        this.journal_id = journal_id;
    }

    public String getJournal_code() {
        return journal_code;
    }

    public void setJournal_code(String journal_code) {
        this.journal_code = journal_code;
    }

    public String getJournal_name() {
        return journal_name;
    }

    public void setJournal_name(String journal_name) {
        this.journal_name = journal_name;
    }

    public Integer getJournal_type() {
        return journal_type;
    }

    public void setJournal_type(Integer journal_type) {
        this.journal_type = journal_type;
    }

}
