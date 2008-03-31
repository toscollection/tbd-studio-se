// ============================================================================
//
// Talend Community Edition
//
// Copyright (C) 2006 Talend – www.talend.com
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
package org.talend.designer.components.persistent;

import java.io.IOException;
import java.util.Comparator;


/**
 * DOC amaumont class global comment. Detailled comment <br/>
 * 
 */
public class PersistentSortedHash<K, V> implements IPersistentHash<K, V> {

    private Comparator<Object> keyValueComparator;

    private String container;

    private KEYS_MANAGEMENT keysManagement;

    public PersistentSortedHash(KEYS_MANAGEMENT keysManagement, String container, Comparator<Object> keyValueComparator) {
        this.keysManagement = keysManagement;
        this.container = container;
        this.keyValueComparator = keyValueComparator;

    }

    public void initPut() throws IOException {

    }

    public void put(V bean) throws IOException {

    }

    public void endPut() throws IOException {

    }

    public void initGet() throws IOException {

    }

    public void lookup(K key) throws Exception {

    }

    public boolean hasNext() throws Exception {
        return false;
    }

    public V next() throws Exception {
        return null;
    }

    public void endGet() throws IOException {

    }

}
