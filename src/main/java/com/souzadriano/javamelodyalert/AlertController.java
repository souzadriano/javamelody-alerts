package com.souzadriano.javamelodyalert;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import javax.xml.parsers.ParserConfigurationException;

import org.jrobin.core.RrdException;
import org.xml.sax.SAXException;

class AlertController {

    private String javaMelodyDirectory;
    private Config config;
    private long ONE_MINUTE_IN_MILLISECONDS = 60l * 1000l;
    private Set<Condition> alertsInProgress = new HashSet<Condition>();
    private String application;

    AlertController(String application) {
        this.application = application;
        javaMelodyDirectory = Parameters.getStorageDirectory(application).getPath();
        try {
            config = LoadConfig.getConfig(Parameters.getStorageDirectoryWithoutApplication());
        } catch (ParserConfigurationException e) {
            // TODO LOG
            e.printStackTrace();
        } catch (SAXException e) {
            // TODO LOG
            e.printStackTrace();
        } catch (IOException e) {
            // TODO LOG
            e.printStackTrace();
        }
    }

    void initialize() {
        if (config != null) {
            for (final Condition condition : config.getConditions()) {
                long milliseconds = condition.getPeriod().longValue() * ONE_MINUTE_IN_MILLISECONDS;
                Timer timer = new Timer(Boolean.TRUE);
                TimerTask timerTask = new TimerTask() {
                    @Override
                    public void run() {
                        doAlert(condition);
                    }
                };
                timer.schedule(timerTask, 6000, milliseconds);
            }
        }
    }

    private void doAlert(Condition condition) {
        try {
            BigDecimal appValue = getValueFromApp(condition.getVariable(), condition.getPeriod());
            if (getResult(condition, appValue)) {
                if (!isAlertInProgress(condition)) {
                    alertsInProgress.add(condition);
                    sendEmailAlertOpen(condition);
                }
            } else if (isAlertInProgress(condition)) {
                alertsInProgress.remove(condition);
                sendEmailAlertClose(condition);
            }
        } catch (RrdException e) {
            // TODO LOG
            e.printStackTrace();
        } catch (IOException e) {
            // TODO LOG
            e.printStackTrace();
        }
    }

    private void sendEmailAlertOpen(Condition condition) {
        System.out.println("Enviar email open " + condition.getVariable());
        // TODO send email
    }

    private void sendEmailAlertClose(Condition condition) {
        System.out.println("Enviar email closed " + condition.getVariable());
        // TODO send email
    }

    private boolean isAlertInProgress(Condition condition) {
        return alertsInProgress.contains(condition);
    }

    private boolean getResult(Condition condition, BigDecimal appValue) throws RrdException, IOException {
        if (appValue != null) {
            int compareTo = appValue.compareTo(condition.getValue());
            switch (condition.getSign()) {
            case EQUAL:
                return compareTo == 0;
            case GREATER_THAN:
                return compareTo == 1;
            case LESS_THAN:
                return compareTo == -1;
            }
        }
        return Boolean.FALSE;
    }

    private BigDecimal getValueFromApp(VariableEnum variable, Integer minutes) throws RrdException, IOException {
        Calendar inicio = Calendar.getInstance();
        inicio.add(Calendar.MINUTE, minutes * (-1));

        Double averageDouble = JRobin.fetch(javaMelodyDirectory + "/" + variable.getVariable() + ".rrd", "AVERAGE",
                inicio.getTimeInMillis() / 1000L,
                Calendar.getInstance().getTimeInMillis() / 1000L)
                .getAggregates(variable.getVariable()).getAverage();

        BigDecimal average = null;

        if (!averageDouble.isNaN()) {
            average = new BigDecimal(averageDouble);

            switch (variable) {
            case GC:
                average = average.multiply(new BigDecimal(100D));
                break;
            case FREE_DISK_SPACE:
            case USEDMEMORY:
            case USEDNONHEAPMEMORY:
            case USEDSWAPSPACESIZE:
                // Transform to MegaBytes
                average = average.divide(new BigDecimal(1000000D));
                break;
            default:
                break;
            }
        }
        return average;
    }
}
