package com.souzadriano.javamelodyalert;

enum VariableEnum {
    ACTIVECONNECTIONS("activeConnections"),
    ACTIVETHREADS("activeThreads"),
    CPU("cpu"),
    FREE_DISK_SPACE("Free_disk_space"),
    GC("gc"),
    HTTPHITSRATE("httpHitsRate"),
    HTTPMEANTIMES("httpMeanTimes"),
    HTTPSESSIONS("httpSessions"),
    HTTPSESSIONSMEANAGE("httpSessionsMeanAge"),
    HTTPSYSTEMERRORS("httpSystemErrors"),
    JSFHITSRATE("jsfHitsRate"),
    JSFMEANTIMES("jsfMeanTimes"),
    JSFSYSTEMERRORS("jsfSystemErrors"),
    JSPHITSRATE("jspHitsRate"),
    JSPMEANTIMES("jspMeanTimes"),
    JSPSYSTEMERRORS("jspSystemErrors"),
    LOADEDCLASSESCOUNT("loadedClassesCount"),
    SQLHITSRATE("sqlHitsRate"),
    SQLMEANTIMES("sqlMeanTimes"),
    SQLSYSTEMERRORS("sqlSystemErrors"),
    THREADCOUNT("threadCount"),
    TRANSACTIONSRATE("transactionsRate"),
    USEDCONNECTIONS("usedConnections"),
    USEDMEMORY("usedMemory"),
    USEDNONHEAPMEMORY("usedNonHeapMemory"),
    // USEDPHYSICALMEMORYSIZE("usedPhysicalMemorySize"),
    USEDSWAPSPACESIZE("usedSwapSpaceSize");

    private VariableEnum(String variable) {
        this.variable = variable;
    }

    private String variable;

    String getVariable() {
        return variable;
    }

    static VariableEnum parseVariable(String variable) {
        for (VariableEnum variableEnum : VariableEnum.values()) {
            if (variableEnum.getVariable().equals(variable)) {
                return variableEnum;
            }
        }
        return null;
    }
}
