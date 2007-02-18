// ============================================================================
//
// Talend Community Edition
//
// Copyright (C) 2006 Talend - www.talend.com
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program; if not, write to the Free Software
// Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
//
// ============================================================================
package routines.system;

public class RunStat implements Runnable {

    private class StatBean {

        private String componentId;

        private int nbLine;

        private int state;

        public StatBean(String componentId) {
            this.componentId = componentId;
        }

        public String getComponentId() {
            return this.componentId;
        }

        public void setComponentId(String componentId) {
            this.componentId = componentId;
        }

        public int getNbLine() {
            return this.nbLine;
        }

        public void setNbLine(int nbLine) {
            this.nbLine = nbLine;
        }

        public int getState() {
            return this.state;
        }

        public void setState(int state) {
            this.state = state;
        }
    }

    private static java.util.HashMap<String, StatBean> processStats = new java.util.HashMap<String, StatBean>();

    private java.net.Socket s;

    private java.io.PrintWriter pred;

    private boolean jobIsFinished = false;

    private String str = "";

    private long startTime = 0;

    private long currentTime = 0;

    public void startThreadStat(int portStats) throws java.io.IOException, java.net.UnknownHostException {
        System.out.println("Connecting to Stat Socket on port " + portStats + "...");
        s = new java.net.Socket("localhost", portStats);
        startTime = java.util.Calendar.getInstance().getTimeInMillis();
        pred = new java.io.PrintWriter(new java.io.BufferedWriter(new java.io.OutputStreamWriter(s.getOutputStream())),
                true);
        System.out.println("Connected to Stat Socket.");
        Thread t = new Thread(this);
        t.start();

    }

    public void run() {
        while (!jobIsFinished) {
            for (StatBean sb : processStats.values()) {
                currentTime = java.util.Calendar.getInstance().getTimeInMillis();
                str = sb.getComponentId() + "|" + sb.getNbLine() + "|" + (currentTime - startTime);
                if (sb.getState() != 1) {
                    str += "|" + ((sb.getState() == 0) ? "start" : "stop");
                    if (sb.getState()==2) {
                        processStats.remove(sb.getComponentId());
                    }
                }
                pred.println(str); // envoi d'un message
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ie) {

            }
        }
    }

    public void stopThreadStat() {
        jobIsFinished = true;
        try {
            pred.close();
            s.close();
        } catch (java.io.IOException ie) {
        }
    }

    public void updateStat(String componentId, int mode, int nbLine) {
        StatBean bean;
        if (processStats.containsKey(componentId)) {
            bean = processStats.get(componentId);
        } else {
            bean = new StatBean(componentId);
        }
        bean.setState(mode);
        bean.setNbLine(bean.getNbLine() + nbLine);
        processStats.put(componentId, bean);
    }
}
