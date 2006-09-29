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
package org.talend.administrator.common.persistence.hibernate;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.talend.administrator.common.exception.DBException;

/**
 * DOC mhirt class global comment. Detailled comment <br/>
 * 
 * $Id$
 * 
 */
public class HibernateTransactionHolder implements ITransactionHolder {

    private Session session;

    private Transaction transaction;

    public HibernateTransactionHolder(Session session, Transaction transaction) {
        this.session = session;
        this.transaction = transaction;
    }

    public void commitAndClose() throws DBException {
        commit();
        close();
    }

    public void close() throws DBException {
        try {
            session.close();
        } catch (HibernateException ex) {
            throw new DBException("Cannot close session.", ex);
        }
    }

    public void commit() throws DBException {
        try {
            session.flush();
            transaction.commit();
        } catch (HibernateException ex) {
            throw new DBException("Cannot commit transaction.", ex);
        }
    }

    public void rollbackAndClose() throws DBException {
        try {
            transaction.rollback();
            session.close();
        } catch (HibernateException ex) {
            throw new DBException("Cannot rollback transaction.", ex);
        }
    }

    public Session getSession() {
        return session;
    }

    public Transaction getTransaction() {
        return transaction;
    }
}
