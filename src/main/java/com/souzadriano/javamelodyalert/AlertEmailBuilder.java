package com.souzadriano.javamelodyalert;

class AlertEmailBuilder {

    static String getSubjectAlertOpen(Condition condition, String application) {
        return I18N.getString("alertopen", condition.getVariable().getVariable(), condition.getSign().getSign(), condition.getValue(), application);
    }

    static String getSubjectAlertClose(Condition condition, String application) {
        return I18N.getString("alertclose", condition.getVariable().getVariable(), condition.getSign().getSign(), condition.getValue(), application);
    }

}
