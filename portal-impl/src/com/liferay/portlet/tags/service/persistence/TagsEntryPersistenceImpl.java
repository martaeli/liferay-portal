/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.portlet.tags.service.persistence;

import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.annotation.BeanReference;
import com.liferay.portal.kernel.cache.CacheRegistry;
import com.liferay.portal.kernel.dao.jdbc.MappingSqlQuery;
import com.liferay.portal.kernel.dao.jdbc.MappingSqlQueryFactoryUtil;
import com.liferay.portal.kernel.dao.jdbc.RowMapper;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdate;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdateFactoryUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.BatchSessionUtil;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.tags.NoSuchEntryException;
import com.liferay.portlet.tags.model.TagsEntry;
import com.liferay.portlet.tags.model.impl.TagsEntryImpl;
import com.liferay.portlet.tags.model.impl.TagsEntryModelImpl;

import java.sql.Types;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <a href="TagsEntryPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class TagsEntryPersistenceImpl extends BasePersistenceImpl
	implements TagsEntryPersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = TagsEntryImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_VOCABULARYID = new FinderPath(TagsEntryModelImpl.ENTITY_CACHE_ENABLED,
			TagsEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByVocabularyId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_VOCABULARYID = new FinderPath(TagsEntryModelImpl.ENTITY_CACHE_ENABLED,
			TagsEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByVocabularyId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_VOCABULARYID = new FinderPath(TagsEntryModelImpl.ENTITY_CACHE_ENABLED,
			TagsEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByVocabularyId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_P_V = new FinderPath(TagsEntryModelImpl.ENTITY_CACHE_ENABLED,
			TagsEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByP_V",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_P_V = new FinderPath(TagsEntryModelImpl.ENTITY_CACHE_ENABLED,
			TagsEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByP_V",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_P_V = new FinderPath(TagsEntryModelImpl.ENTITY_CACHE_ENABLED,
			TagsEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByP_V",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(TagsEntryModelImpl.ENTITY_CACHE_ENABLED,
			TagsEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(TagsEntryModelImpl.ENTITY_CACHE_ENABLED,
			TagsEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countAll", new String[0]);

	public void cacheResult(TagsEntry tagsEntry) {
		EntityCacheUtil.putResult(TagsEntryModelImpl.ENTITY_CACHE_ENABLED,
			TagsEntryImpl.class, tagsEntry.getPrimaryKey(), tagsEntry);
	}

	public void cacheResult(List<TagsEntry> tagsEntries) {
		for (TagsEntry tagsEntry : tagsEntries) {
			if (EntityCacheUtil.getResult(
						TagsEntryModelImpl.ENTITY_CACHE_ENABLED,
						TagsEntryImpl.class, tagsEntry.getPrimaryKey(), this) == null) {
				cacheResult(tagsEntry);
			}
		}
	}

	public void clearCache() {
		CacheRegistry.clear(TagsEntryImpl.class.getName());
		EntityCacheUtil.clearCache(TagsEntryImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	public TagsEntry create(long entryId) {
		TagsEntry tagsEntry = new TagsEntryImpl();

		tagsEntry.setNew(true);
		tagsEntry.setPrimaryKey(entryId);

		return tagsEntry;
	}

	public TagsEntry remove(long entryId)
		throws NoSuchEntryException, SystemException {
		Session session = null;

		try {
			session = openSession();

			TagsEntry tagsEntry = (TagsEntry)session.get(TagsEntryImpl.class,
					new Long(entryId));

			if (tagsEntry == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No TagsEntry exists with the primary key " +
						entryId);
				}

				throw new NoSuchEntryException(
					"No TagsEntry exists with the primary key " + entryId);
			}

			return remove(tagsEntry);
		}
		catch (NoSuchEntryException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public TagsEntry remove(TagsEntry tagsEntry) throws SystemException {
		for (ModelListener<TagsEntry> listener : listeners) {
			listener.onBeforeRemove(tagsEntry);
		}

		tagsEntry = removeImpl(tagsEntry);

		for (ModelListener<TagsEntry> listener : listeners) {
			listener.onAfterRemove(tagsEntry);
		}

		return tagsEntry;
	}

	protected TagsEntry removeImpl(TagsEntry tagsEntry)
		throws SystemException {
		try {
			clearTagsAssets.clear(tagsEntry.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("TagsAssets_TagsEntries");
		}

		Session session = null;

		try {
			session = openSession();

			if (tagsEntry.isCachedModel() || BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(TagsEntryImpl.class,
						tagsEntry.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(tagsEntry);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.removeResult(TagsEntryModelImpl.ENTITY_CACHE_ENABLED,
			TagsEntryImpl.class, tagsEntry.getPrimaryKey());

		return tagsEntry;
	}

	/**
	 * @deprecated Use <code>update(TagsEntry tagsEntry, boolean merge)</code>.
	 */
	public TagsEntry update(TagsEntry tagsEntry) throws SystemException {
		if (_log.isWarnEnabled()) {
			_log.warn(
				"Using the deprecated update(TagsEntry tagsEntry) method. Use update(TagsEntry tagsEntry, boolean merge) instead.");
		}

		return update(tagsEntry, false);
	}

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param        tagsEntry the entity to add, update, or merge
	 * @param        merge boolean value for whether to merge the entity. The
	 *                default value is false. Setting merge to true is more
	 *                expensive and should only be true when tagsEntry is
	 *                transient. See LEP-5473 for a detailed discussion of this
	 *                method.
	 * @return        true if the portlet can be displayed via Ajax
	 */
	public TagsEntry update(TagsEntry tagsEntry, boolean merge)
		throws SystemException {
		boolean isNew = tagsEntry.isNew();

		for (ModelListener<TagsEntry> listener : listeners) {
			if (isNew) {
				listener.onBeforeCreate(tagsEntry);
			}
			else {
				listener.onBeforeUpdate(tagsEntry);
			}
		}

		tagsEntry = updateImpl(tagsEntry, merge);

		for (ModelListener<TagsEntry> listener : listeners) {
			if (isNew) {
				listener.onAfterCreate(tagsEntry);
			}
			else {
				listener.onAfterUpdate(tagsEntry);
			}
		}

		return tagsEntry;
	}

	public TagsEntry updateImpl(
		com.liferay.portlet.tags.model.TagsEntry tagsEntry, boolean merge)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, tagsEntry, merge);

			tagsEntry.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(TagsEntryModelImpl.ENTITY_CACHE_ENABLED,
			TagsEntryImpl.class, tagsEntry.getPrimaryKey(), tagsEntry);

		return tagsEntry;
	}

	public TagsEntry findByPrimaryKey(long entryId)
		throws NoSuchEntryException, SystemException {
		TagsEntry tagsEntry = fetchByPrimaryKey(entryId);

		if (tagsEntry == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No TagsEntry exists with the primary key " +
					entryId);
			}

			throw new NoSuchEntryException(
				"No TagsEntry exists with the primary key " + entryId);
		}

		return tagsEntry;
	}

	public TagsEntry fetchByPrimaryKey(long entryId) throws SystemException {
		TagsEntry tagsEntry = (TagsEntry)EntityCacheUtil.getResult(TagsEntryModelImpl.ENTITY_CACHE_ENABLED,
				TagsEntryImpl.class, entryId, this);

		if (tagsEntry == null) {
			Session session = null;

			try {
				session = openSession();

				tagsEntry = (TagsEntry)session.get(TagsEntryImpl.class,
						new Long(entryId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (tagsEntry != null) {
					cacheResult(tagsEntry);
				}

				closeSession(session);
			}
		}

		return tagsEntry;
	}

	public List<TagsEntry> findByVocabularyId(long vocabularyId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(vocabularyId) };

		List<TagsEntry> list = (List<TagsEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_VOCABULARYID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT tagsEntry FROM TagsEntry tagsEntry WHERE ");

				query.append("tagsEntry.vocabularyId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("tagsEntry.name ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(vocabularyId);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<TagsEntry>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_VOCABULARYID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<TagsEntry> findByVocabularyId(long vocabularyId, int start,
		int end) throws SystemException {
		return findByVocabularyId(vocabularyId, start, end, null);
	}

	public List<TagsEntry> findByVocabularyId(long vocabularyId, int start,
		int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(vocabularyId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<TagsEntry> list = (List<TagsEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_VOCABULARYID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT tagsEntry FROM TagsEntry tagsEntry WHERE ");

				query.append("tagsEntry.vocabularyId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("tagsEntry.");
						query.append(orderByFields[i]);

						if (obc.isAscending()) {
							query.append(" ASC");
						}
						else {
							query.append(" DESC");
						}

						if ((i + 1) < orderByFields.length) {
							query.append(", ");
						}
					}
				}

				else {
					query.append("ORDER BY ");

					query.append("tagsEntry.name ASC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(vocabularyId);

				list = (List<TagsEntry>)QueryUtil.list(q, getDialect(), start,
						end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<TagsEntry>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_VOCABULARYID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public TagsEntry findByVocabularyId_First(long vocabularyId,
		OrderByComparator obc) throws NoSuchEntryException, SystemException {
		List<TagsEntry> list = findByVocabularyId(vocabularyId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No TagsEntry exists with the key {");

			msg.append("vocabularyId=" + vocabularyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public TagsEntry findByVocabularyId_Last(long vocabularyId,
		OrderByComparator obc) throws NoSuchEntryException, SystemException {
		int count = countByVocabularyId(vocabularyId);

		List<TagsEntry> list = findByVocabularyId(vocabularyId, count - 1,
				count, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No TagsEntry exists with the key {");

			msg.append("vocabularyId=" + vocabularyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public TagsEntry[] findByVocabularyId_PrevAndNext(long entryId,
		long vocabularyId, OrderByComparator obc)
		throws NoSuchEntryException, SystemException {
		TagsEntry tagsEntry = findByPrimaryKey(entryId);

		int count = countByVocabularyId(vocabularyId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append("SELECT tagsEntry FROM TagsEntry tagsEntry WHERE ");

			query.append("tagsEntry.vocabularyId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("tagsEntry.");
					query.append(orderByFields[i]);

					if (obc.isAscending()) {
						query.append(" ASC");
					}
					else {
						query.append(" DESC");
					}

					if ((i + 1) < orderByFields.length) {
						query.append(", ");
					}
				}
			}

			else {
				query.append("ORDER BY ");

				query.append("tagsEntry.name ASC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(vocabularyId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					tagsEntry);

			TagsEntry[] array = new TagsEntryImpl[3];

			array[0] = (TagsEntry)objArray[0];
			array[1] = (TagsEntry)objArray[1];
			array[2] = (TagsEntry)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<TagsEntry> findByP_V(long parentEntryId, long vocabularyId)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(parentEntryId), new Long(vocabularyId)
			};

		List<TagsEntry> list = (List<TagsEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_P_V,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT tagsEntry FROM TagsEntry tagsEntry WHERE ");

				query.append("tagsEntry.parentEntryId = ?");

				query.append(" AND ");

				query.append("tagsEntry.vocabularyId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("tagsEntry.name ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(parentEntryId);

				qPos.add(vocabularyId);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<TagsEntry>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_P_V, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<TagsEntry> findByP_V(long parentEntryId, long vocabularyId,
		int start, int end) throws SystemException {
		return findByP_V(parentEntryId, vocabularyId, start, end, null);
	}

	public List<TagsEntry> findByP_V(long parentEntryId, long vocabularyId,
		int start, int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(parentEntryId), new Long(vocabularyId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<TagsEntry> list = (List<TagsEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_P_V,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT tagsEntry FROM TagsEntry tagsEntry WHERE ");

				query.append("tagsEntry.parentEntryId = ?");

				query.append(" AND ");

				query.append("tagsEntry.vocabularyId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("tagsEntry.");
						query.append(orderByFields[i]);

						if (obc.isAscending()) {
							query.append(" ASC");
						}
						else {
							query.append(" DESC");
						}

						if ((i + 1) < orderByFields.length) {
							query.append(", ");
						}
					}
				}

				else {
					query.append("ORDER BY ");

					query.append("tagsEntry.name ASC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(parentEntryId);

				qPos.add(vocabularyId);

				list = (List<TagsEntry>)QueryUtil.list(q, getDialect(), start,
						end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<TagsEntry>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_P_V,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public TagsEntry findByP_V_First(long parentEntryId, long vocabularyId,
		OrderByComparator obc) throws NoSuchEntryException, SystemException {
		List<TagsEntry> list = findByP_V(parentEntryId, vocabularyId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No TagsEntry exists with the key {");

			msg.append("parentEntryId=" + parentEntryId);

			msg.append(", ");
			msg.append("vocabularyId=" + vocabularyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public TagsEntry findByP_V_Last(long parentEntryId, long vocabularyId,
		OrderByComparator obc) throws NoSuchEntryException, SystemException {
		int count = countByP_V(parentEntryId, vocabularyId);

		List<TagsEntry> list = findByP_V(parentEntryId, vocabularyId,
				count - 1, count, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No TagsEntry exists with the key {");

			msg.append("parentEntryId=" + parentEntryId);

			msg.append(", ");
			msg.append("vocabularyId=" + vocabularyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public TagsEntry[] findByP_V_PrevAndNext(long entryId, long parentEntryId,
		long vocabularyId, OrderByComparator obc)
		throws NoSuchEntryException, SystemException {
		TagsEntry tagsEntry = findByPrimaryKey(entryId);

		int count = countByP_V(parentEntryId, vocabularyId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append("SELECT tagsEntry FROM TagsEntry tagsEntry WHERE ");

			query.append("tagsEntry.parentEntryId = ?");

			query.append(" AND ");

			query.append("tagsEntry.vocabularyId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("tagsEntry.");
					query.append(orderByFields[i]);

					if (obc.isAscending()) {
						query.append(" ASC");
					}
					else {
						query.append(" DESC");
					}

					if ((i + 1) < orderByFields.length) {
						query.append(", ");
					}
				}
			}

			else {
				query.append("ORDER BY ");

				query.append("tagsEntry.name ASC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(parentEntryId);

			qPos.add(vocabularyId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					tagsEntry);

			TagsEntry[] array = new TagsEntryImpl[3];

			array[0] = (TagsEntry)objArray[0];
			array[1] = (TagsEntry)objArray[1];
			array[2] = (TagsEntry)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<Object> findWithDynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			dynamicQuery.compile(session);

			return dynamicQuery.list();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<Object> findWithDynamicQuery(DynamicQuery dynamicQuery,
		int start, int end) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			dynamicQuery.setLimit(start, end);

			dynamicQuery.compile(session);

			return dynamicQuery.list();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<TagsEntry> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<TagsEntry> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<TagsEntry> findAll(int start, int end, OrderByComparator obc)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<TagsEntry> list = (List<TagsEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT tagsEntry FROM TagsEntry tagsEntry ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("tagsEntry.");
						query.append(orderByFields[i]);

						if (obc.isAscending()) {
							query.append(" ASC");
						}
						else {
							query.append(" DESC");
						}

						if ((i + 1) < orderByFields.length) {
							query.append(", ");
						}
					}
				}

				else {
					query.append("ORDER BY ");

					query.append("tagsEntry.name ASC");
				}

				Query q = session.createQuery(query.toString());

				if (obc == null) {
					list = (List<TagsEntry>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<TagsEntry>)QueryUtil.list(q, getDialect(),
							start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<TagsEntry>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public void removeByVocabularyId(long vocabularyId)
		throws SystemException {
		for (TagsEntry tagsEntry : findByVocabularyId(vocabularyId)) {
			remove(tagsEntry);
		}
	}

	public void removeByP_V(long parentEntryId, long vocabularyId)
		throws SystemException {
		for (TagsEntry tagsEntry : findByP_V(parentEntryId, vocabularyId)) {
			remove(tagsEntry);
		}
	}

	public void removeAll() throws SystemException {
		for (TagsEntry tagsEntry : findAll()) {
			remove(tagsEntry);
		}
	}

	public int countByVocabularyId(long vocabularyId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(vocabularyId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_VOCABULARYID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(tagsEntry) ");
				query.append("FROM TagsEntry tagsEntry WHERE ");

				query.append("tagsEntry.vocabularyId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(vocabularyId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_VOCABULARYID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByP_V(long parentEntryId, long vocabularyId)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(parentEntryId), new Long(vocabularyId)
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_P_V,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(tagsEntry) ");
				query.append("FROM TagsEntry tagsEntry WHERE ");

				query.append("tagsEntry.parentEntryId = ?");

				query.append(" AND ");

				query.append("tagsEntry.vocabularyId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(parentEntryId);

				qPos.add(vocabularyId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_P_V, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countAll() throws SystemException {
		Object[] finderArgs = new Object[0];

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(
						"SELECT COUNT(tagsEntry) FROM TagsEntry tagsEntry");

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_ALL, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public List<com.liferay.portlet.tags.model.TagsAsset> getTagsAssets(long pk)
		throws SystemException {
		return getTagsAssets(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	public List<com.liferay.portlet.tags.model.TagsAsset> getTagsAssets(
		long pk, int start, int end) throws SystemException {
		return getTagsAssets(pk, start, end, null);
	}

	public static final FinderPath FINDER_PATH_GET_TAGSASSETS = new FinderPath(com.liferay.portlet.tags.model.impl.TagsAssetModelImpl.ENTITY_CACHE_ENABLED,
			TagsEntryModelImpl.FINDER_CACHE_ENABLED_TAGSASSETS_TAGSENTRIES,
			"TagsAssets_TagsEntries", "getTagsAssets",
			new String[] {
				Long.class.getName(), "java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});

	public List<com.liferay.portlet.tags.model.TagsAsset> getTagsAssets(
		long pk, int start, int end, OrderByComparator obc)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(pk), String.valueOf(start), String.valueOf(end),
				String.valueOf(obc)
			};

		List<com.liferay.portlet.tags.model.TagsAsset> list = (List<com.liferay.portlet.tags.model.TagsAsset>)FinderCacheUtil.getResult(FINDER_PATH_GET_TAGSASSETS,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder sb = new StringBuilder();

				sb.append(_SQL_GETTAGSASSETS);

				if (obc != null) {
					sb.append("ORDER BY ");
					sb.append(obc.getOrderBy());
				}

				String sql = sb.toString();

				SQLQuery q = session.createSQLQuery(sql);

				q.addEntity("TagsAsset",
					com.liferay.portlet.tags.model.impl.TagsAssetImpl.class);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(pk);

				list = (List<com.liferay.portlet.tags.model.TagsAsset>)QueryUtil.list(q,
						getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<com.liferay.portlet.tags.model.TagsAsset>();
				}

				tagsAssetPersistence.cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_GET_TAGSASSETS,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public static final FinderPath FINDER_PATH_GET_TAGSASSETS_SIZE = new FinderPath(com.liferay.portlet.tags.model.impl.TagsAssetModelImpl.ENTITY_CACHE_ENABLED,
			TagsEntryModelImpl.FINDER_CACHE_ENABLED_TAGSASSETS_TAGSENTRIES,
			"TagsAssets_TagsEntries", "getTagsAssetsSize",
			new String[] { Long.class.getName() });

	public int getTagsAssetsSize(long pk) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(pk) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_GET_TAGSASSETS_SIZE,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				SQLQuery q = session.createSQLQuery(_SQL_GETTAGSASSETSSIZE);

				q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(pk);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_GET_TAGSASSETS_SIZE,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public static final FinderPath FINDER_PATH_CONTAINS_TAGSASSET = new FinderPath(com.liferay.portlet.tags.model.impl.TagsAssetModelImpl.ENTITY_CACHE_ENABLED,
			TagsEntryModelImpl.FINDER_CACHE_ENABLED_TAGSASSETS_TAGSENTRIES,
			"TagsAssets_TagsEntries", "containsTagsAsset",
			new String[] { Long.class.getName(), Long.class.getName() });

	public boolean containsTagsAsset(long pk, long tagsAssetPK)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(pk), new Long(tagsAssetPK) };

		Boolean value = (Boolean)FinderCacheUtil.getResult(FINDER_PATH_CONTAINS_TAGSASSET,
				finderArgs, this);

		if (value == null) {
			try {
				value = Boolean.valueOf(containsTagsAsset.contains(pk,
							tagsAssetPK));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (value == null) {
					value = Boolean.FALSE;
				}

				FinderCacheUtil.putResult(FINDER_PATH_CONTAINS_TAGSASSET,
					finderArgs, value);
			}
		}

		return value.booleanValue();
	}

	public boolean containsTagsAssets(long pk) throws SystemException {
		if (getTagsAssetsSize(pk) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	public void addTagsAsset(long pk, long tagsAssetPK)
		throws SystemException {
		try {
			addTagsAsset.add(pk, tagsAssetPK);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("TagsAssets_TagsEntries");
		}
	}

	public void addTagsAsset(long pk,
		com.liferay.portlet.tags.model.TagsAsset tagsAsset)
		throws SystemException {
		try {
			addTagsAsset.add(pk, tagsAsset.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("TagsAssets_TagsEntries");
		}
	}

	public void addTagsAssets(long pk, long[] tagsAssetPKs)
		throws SystemException {
		try {
			for (long tagsAssetPK : tagsAssetPKs) {
				addTagsAsset.add(pk, tagsAssetPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("TagsAssets_TagsEntries");
		}
	}

	public void addTagsAssets(long pk,
		List<com.liferay.portlet.tags.model.TagsAsset> tagsAssets)
		throws SystemException {
		try {
			for (com.liferay.portlet.tags.model.TagsAsset tagsAsset : tagsAssets) {
				addTagsAsset.add(pk, tagsAsset.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("TagsAssets_TagsEntries");
		}
	}

	public void clearTagsAssets(long pk) throws SystemException {
		try {
			clearTagsAssets.clear(pk);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("TagsAssets_TagsEntries");
		}
	}

	public void removeTagsAsset(long pk, long tagsAssetPK)
		throws SystemException {
		try {
			removeTagsAsset.remove(pk, tagsAssetPK);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("TagsAssets_TagsEntries");
		}
	}

	public void removeTagsAsset(long pk,
		com.liferay.portlet.tags.model.TagsAsset tagsAsset)
		throws SystemException {
		try {
			removeTagsAsset.remove(pk, tagsAsset.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("TagsAssets_TagsEntries");
		}
	}

	public void removeTagsAssets(long pk, long[] tagsAssetPKs)
		throws SystemException {
		try {
			for (long tagsAssetPK : tagsAssetPKs) {
				removeTagsAsset.remove(pk, tagsAssetPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("TagsAssets_TagsEntries");
		}
	}

	public void removeTagsAssets(long pk,
		List<com.liferay.portlet.tags.model.TagsAsset> tagsAssets)
		throws SystemException {
		try {
			for (com.liferay.portlet.tags.model.TagsAsset tagsAsset : tagsAssets) {
				removeTagsAsset.remove(pk, tagsAsset.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("TagsAssets_TagsEntries");
		}
	}

	public void setTagsAssets(long pk, long[] tagsAssetPKs)
		throws SystemException {
		try {
			clearTagsAssets.clear(pk);

			for (long tagsAssetPK : tagsAssetPKs) {
				addTagsAsset.add(pk, tagsAssetPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("TagsAssets_TagsEntries");
		}
	}

	public void setTagsAssets(long pk,
		List<com.liferay.portlet.tags.model.TagsAsset> tagsAssets)
		throws SystemException {
		try {
			clearTagsAssets.clear(pk);

			for (com.liferay.portlet.tags.model.TagsAsset tagsAsset : tagsAssets) {
				addTagsAsset.add(pk, tagsAsset.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache("TagsAssets_TagsEntries");
		}
	}

	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portlet.tags.model.TagsEntry")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<TagsEntry>> listenersList = new ArrayList<ModelListener<TagsEntry>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<TagsEntry>)Class.forName(
							listenerClassName).newInstance());
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}

		containsTagsAsset = new ContainsTagsAsset(this);

		addTagsAsset = new AddTagsAsset(this);
		clearTagsAssets = new ClearTagsAssets(this);
		removeTagsAsset = new RemoveTagsAsset(this);
	}

	@BeanReference(name = "com.liferay.portlet.tags.service.persistence.TagsAssetPersistence.impl")
	protected com.liferay.portlet.tags.service.persistence.TagsAssetPersistence tagsAssetPersistence;
	@BeanReference(name = "com.liferay.portlet.tags.service.persistence.TagsEntryPersistence.impl")
	protected com.liferay.portlet.tags.service.persistence.TagsEntryPersistence tagsEntryPersistence;
	@BeanReference(name = "com.liferay.portlet.tags.service.persistence.TagsPropertyPersistence.impl")
	protected com.liferay.portlet.tags.service.persistence.TagsPropertyPersistence tagsPropertyPersistence;
	@BeanReference(name = "com.liferay.portlet.tags.service.persistence.TagsSourcePersistence.impl")
	protected com.liferay.portlet.tags.service.persistence.TagsSourcePersistence tagsSourcePersistence;
	@BeanReference(name = "com.liferay.portlet.tags.service.persistence.TagsVocabularyPersistence.impl")
	protected com.liferay.portlet.tags.service.persistence.TagsVocabularyPersistence tagsVocabularyPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourcePersistence.impl")
	protected com.liferay.portal.service.persistence.ResourcePersistence resourcePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserPersistence.impl")
	protected com.liferay.portal.service.persistence.UserPersistence userPersistence;
	protected ContainsTagsAsset containsTagsAsset;
	protected AddTagsAsset addTagsAsset;
	protected ClearTagsAssets clearTagsAssets;
	protected RemoveTagsAsset removeTagsAsset;

	protected class ContainsTagsAsset {
		protected ContainsTagsAsset(TagsEntryPersistenceImpl persistenceImpl) {
			super();

			_mappingSqlQuery = MappingSqlQueryFactoryUtil.getMappingSqlQuery(getDataSource(),
					_SQL_CONTAINSTAGSASSET,
					new int[] { Types.BIGINT, Types.BIGINT }, RowMapper.COUNT);
		}

		protected boolean contains(long entryId, long assetId) {
			List<Integer> results = _mappingSqlQuery.execute(new Object[] {
						new Long(entryId), new Long(assetId)
					});

			if (results.size() > 0) {
				Integer count = results.get(0);

				if (count.intValue() > 0) {
					return true;
				}
			}

			return false;
		}

		private MappingSqlQuery _mappingSqlQuery;
	}

	protected class AddTagsAsset {
		protected AddTagsAsset(TagsEntryPersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"INSERT INTO TagsAssets_TagsEntries (entryId, assetId) VALUES (?, ?)",
					new int[] { Types.BIGINT, Types.BIGINT });
			_persistenceImpl = persistenceImpl;
		}

		protected void add(long entryId, long assetId)
			throws SystemException {
			if (!_persistenceImpl.containsTagsAsset.contains(entryId, assetId)) {
				ModelListener<com.liferay.portlet.tags.model.TagsAsset>[] tagsAssetListeners =
					tagsAssetPersistence.getListeners();

				for (ModelListener<TagsEntry> listener : listeners) {
					listener.onBeforeAddAssociation(entryId,
						com.liferay.portlet.tags.model.TagsAsset.class.getName(),
						assetId);
				}

				for (ModelListener<com.liferay.portlet.tags.model.TagsAsset> listener : tagsAssetListeners) {
					listener.onBeforeAddAssociation(assetId,
						TagsEntry.class.getName(), entryId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(entryId), new Long(assetId)
					});

				for (ModelListener<TagsEntry> listener : listeners) {
					listener.onAfterAddAssociation(entryId,
						com.liferay.portlet.tags.model.TagsAsset.class.getName(),
						assetId);
				}

				for (ModelListener<com.liferay.portlet.tags.model.TagsAsset> listener : tagsAssetListeners) {
					listener.onAfterAddAssociation(assetId,
						TagsEntry.class.getName(), entryId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
		private TagsEntryPersistenceImpl _persistenceImpl;
	}

	protected class ClearTagsAssets {
		protected ClearTagsAssets(TagsEntryPersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM TagsAssets_TagsEntries WHERE entryId = ?",
					new int[] { Types.BIGINT });
		}

		protected void clear(long entryId) throws SystemException {
			ModelListener<com.liferay.portlet.tags.model.TagsAsset>[] tagsAssetListeners =
				tagsAssetPersistence.getListeners();

			List<com.liferay.portlet.tags.model.TagsAsset> tagsAssets = null;

			if ((listeners.length > 0) || (tagsAssetListeners.length > 0)) {
				tagsAssets = getTagsAssets(entryId);

				for (com.liferay.portlet.tags.model.TagsAsset tagsAsset : tagsAssets) {
					for (ModelListener<TagsEntry> listener : listeners) {
						listener.onBeforeRemoveAssociation(entryId,
							com.liferay.portlet.tags.model.TagsAsset.class.getName(),
							tagsAsset.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portlet.tags.model.TagsAsset> listener : tagsAssetListeners) {
						listener.onBeforeRemoveAssociation(tagsAsset.getPrimaryKey(),
							TagsEntry.class.getName(), entryId);
					}
				}
			}

			_sqlUpdate.update(new Object[] { new Long(entryId) });

			if ((listeners.length > 0) || (tagsAssetListeners.length > 0)) {
				for (com.liferay.portlet.tags.model.TagsAsset tagsAsset : tagsAssets) {
					for (ModelListener<TagsEntry> listener : listeners) {
						listener.onAfterRemoveAssociation(entryId,
							com.liferay.portlet.tags.model.TagsAsset.class.getName(),
							tagsAsset.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portlet.tags.model.TagsAsset> listener : tagsAssetListeners) {
						listener.onBeforeRemoveAssociation(tagsAsset.getPrimaryKey(),
							TagsEntry.class.getName(), entryId);
					}
				}
			}
		}

		private SqlUpdate _sqlUpdate;
	}

	protected class RemoveTagsAsset {
		protected RemoveTagsAsset(TagsEntryPersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM TagsAssets_TagsEntries WHERE entryId = ? AND assetId = ?",
					new int[] { Types.BIGINT, Types.BIGINT });
			_persistenceImpl = persistenceImpl;
		}

		protected void remove(long entryId, long assetId)
			throws SystemException {
			if (_persistenceImpl.containsTagsAsset.contains(entryId, assetId)) {
				ModelListener<com.liferay.portlet.tags.model.TagsAsset>[] tagsAssetListeners =
					tagsAssetPersistence.getListeners();

				for (ModelListener<TagsEntry> listener : listeners) {
					listener.onBeforeRemoveAssociation(entryId,
						com.liferay.portlet.tags.model.TagsAsset.class.getName(),
						assetId);
				}

				for (ModelListener<com.liferay.portlet.tags.model.TagsAsset> listener : tagsAssetListeners) {
					listener.onBeforeRemoveAssociation(assetId,
						TagsEntry.class.getName(), entryId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(entryId), new Long(assetId)
					});

				for (ModelListener<TagsEntry> listener : listeners) {
					listener.onAfterRemoveAssociation(entryId,
						com.liferay.portlet.tags.model.TagsAsset.class.getName(),
						assetId);
				}

				for (ModelListener<com.liferay.portlet.tags.model.TagsAsset> listener : tagsAssetListeners) {
					listener.onAfterRemoveAssociation(assetId,
						TagsEntry.class.getName(), entryId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
		private TagsEntryPersistenceImpl _persistenceImpl;
	}

	private static final String _SQL_GETTAGSASSETS = "SELECT {TagsAsset.*} FROM TagsAsset INNER JOIN TagsAssets_TagsEntries ON (TagsAssets_TagsEntries.assetId = TagsAsset.assetId) WHERE (TagsAssets_TagsEntries.entryId = ?)";
	private static final String _SQL_GETTAGSASSETSSIZE = "SELECT COUNT(*) AS COUNT_VALUE FROM TagsAssets_TagsEntries WHERE entryId = ?";
	private static final String _SQL_CONTAINSTAGSASSET = "SELECT COUNT(*) AS COUNT_VALUE FROM TagsAssets_TagsEntries WHERE entryId = ? AND assetId = ?";
	private static Log _log = LogFactoryUtil.getLog(TagsEntryPersistenceImpl.class);
}