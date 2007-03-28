// ============================================================================
//
// Talend Community Edition
//
// Copyright (C) 2006-2007 Talend - www.talend.com
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License.
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

import java.util.Date;

public class StatCatcherUtils {
    public class StatCatcherMessage {
        private String origin;
        private String message;
        private Date moment;
        
        public StatCatcherMessage(String message, String origin) {
            this.origin = origin;
            this.message = message;
            this.moment = java.util.Calendar.getInstance().getTime(); 
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getOrigin() {
            return origin;
        }

        public void setOrigin(String origin) {
            this.origin = origin;
        }
        
        public Date getMoment() {
            return moment;
        }

        public void setMoment(Date d) {
            this.moment = d;
        }
    }

    java.util.List<StatCatcherMessage> messages = new java.util.ArrayList<StatCatcherMessage>();

    public void addMessage(String message, String origin) {
        StatCatcherMessage scm = new StatCatcherMessage(message, origin);
        messages.add(scm);
    }
    
    public void addMessage(String message) {
        addMessage(message, "");
    }

    public java.util.List<StatCatcherMessage> getMessages() {
        java.util.List<StatCatcherMessage> messagesToSend = new java.util.ArrayList<StatCatcherMessage>();
        for (StatCatcherMessage scm : messages) {
            messagesToSend.add(scm);
        }
        messages.clear();
        return messagesToSend;
    }
}
