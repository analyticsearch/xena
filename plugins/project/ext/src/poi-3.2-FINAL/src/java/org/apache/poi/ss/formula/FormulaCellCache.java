/* ====================================================================
   Licensed to the Apache Software Foundation (ASF) under one or more
   contributor license agreements.  See the NOTICE file distributed with
   this work for additional information regarding copyright ownership.
   The ASF licenses this file to You under the Apache License, Version 2.0
   (the "License"); you may not use this file except in compliance with
   the License.  You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
==================================================================== */

package org.apache.poi.ss.formula;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 
 * @author Josh Micich
 */
final class FormulaCellCache {

	static interface IEntryOperation {
		void processEntry(FormulaCellCacheEntry entry);
	}

	private Map _formulaEntriesByCell;

	public FormulaCellCache() {
		// assumes HSSFCell does not override hashCode or equals, otherwise we need IdentityHashMap
		_formulaEntriesByCell = new HashMap(); 
	}

	public CellCacheEntry[] getCacheEntries() {

		FormulaCellCacheEntry[] result = new FormulaCellCacheEntry[_formulaEntriesByCell.size()];
		_formulaEntriesByCell.values().toArray(result);
		return result;
	}

	public void clear() {
		_formulaEntriesByCell.clear();
	}

	/**
	 * @return <code>null</code> if not found
	 */
	public FormulaCellCacheEntry get(EvaluationCell cell) {
		return (FormulaCellCacheEntry) _formulaEntriesByCell.get(cell);
	}

	public void put(EvaluationCell cell, FormulaCellCacheEntry entry) {
		_formulaEntriesByCell.put(cell, entry);
	}

	public FormulaCellCacheEntry remove(EvaluationCell cell) {
		return (FormulaCellCacheEntry) _formulaEntriesByCell.remove(cell);
	}

	public void applyOperation(IEntryOperation operation) {
		Iterator i = _formulaEntriesByCell.values().iterator();
		while (i.hasNext()) {
			operation.processEntry((FormulaCellCacheEntry) i.next());
		}
	}
}
