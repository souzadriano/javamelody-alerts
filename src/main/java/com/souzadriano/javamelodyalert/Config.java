package com.souzadriano.javamelodyalert;

import java.util.Collection;

class Config {

    private Collection<String> emails;
    private Collection<Condition> conditions;

    Collection<String> getEmails() {
        return emails;
    }

    void setEmails(Collection<String> emails) {
        this.emails = emails;
    }

    Collection<Condition> getConditions() {
        return conditions;
    }

    void setConditions(Collection<Condition> conditions) {
        this.conditions = conditions;
    }

}
