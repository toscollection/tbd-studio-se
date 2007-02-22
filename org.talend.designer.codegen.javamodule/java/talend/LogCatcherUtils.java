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

public class LogCatcherUtils {
    public class LogCatcherMessage {
        private String type;
        private String origin;
        private int priority;
        private String errorMessage;
        private String javaErrorMessage;
        private int javaErrorCode;

        public LogCatcherMessage(String type, String origin, int priority, String error_message,
                String java_error_message, int java_error_code) {
            this.type = type;
            this.origin = origin;
            this.priority = priority;
            this.errorMessage = error_message;
            this.javaErrorMessage = java_error_message;
            this.javaErrorCode = java_error_code;
        }

        public String getErrorMessage() {
            return errorMessage;
        }

        public void setErrorMessage(String error_message) {
            this.errorMessage = error_message;
        }

        public int getJavaErrorCode() {
            return javaErrorCode;
        }

        public void setJavaErrorCode(int java_error_code) {
            this.javaErrorCode = java_error_code;
        }

        public String getJavaErrorMessage() {
            return javaErrorMessage;
        }

        public void setJavaErrorMessage(String java_error_message) {
            this.javaErrorMessage = java_error_message;
        }

        public String getOrigin() {
            return origin;
        }

        public void setOrigin(String origin) {
            this.origin = origin;
        }

        public int getPriority() {
            return priority;
        }

        public void setPriority(int priority) {
            this.priority = priority;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

    java.util.List<LogCatcherMessage> messages = new java.util.ArrayList<LogCatcherMessage>();

    public void addMessage(String type, String origin, int priority, String error_message,
            String java_error_message, int java_error_code) {
        LogCatcherMessage lcm = new LogCatcherMessage(type, origin, priority, error_message, java_error_message,
                java_error_code);
        messages.add(lcm);
    }

    public java.util.List<LogCatcherMessage> getMessages() {
        java.util.List<LogCatcherMessage> messagesToSend = new java.util.ArrayList<LogCatcherMessage>();
        for (LogCatcherMessage lcm : messages) {
            messagesToSend.add(lcm);
        }
        messages.clear();
        return messagesToSend;
    }
}
