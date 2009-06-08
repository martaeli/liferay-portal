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

package com.liferay.portlet.asset.service.persistence;

/**
 * <a href="AssetCategoryUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class AssetCategoryUtil {
	public static void cacheResult(
		com.liferay.portlet.asset.model.AssetCategory assetCategory) {
		getPersistence().cacheResult(assetCategory);
	}

	public static void cacheResult(
		java.util.List<com.liferay.portlet.asset.model.AssetCategory> assetCategories) {
		getPersistence().cacheResult(assetCategories);
	}

	public static void clearCache() {
		getPersistence().clearCache();
	}

	public static com.liferay.portlet.asset.model.AssetCategory create(
		long categoryId) {
		return getPersistence().create(categoryId);
	}

	public static com.liferay.portlet.asset.model.AssetCategory remove(
		long categoryId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.asset.NoSuchCategoryException {
		return getPersistence().remove(categoryId);
	}

	public static com.liferay.portlet.asset.model.AssetCategory remove(
		com.liferay.portlet.asset.model.AssetCategory assetCategory)
		throws com.liferay.portal.SystemException {
		return getPersistence().remove(assetCategory);
	}

	/**
	 * @deprecated Use <code>update(AssetCategory assetCategory, boolean merge)</code>.
	 */
	public static com.liferay.portlet.asset.model.AssetCategory update(
		com.liferay.portlet.asset.model.AssetCategory assetCategory)
		throws com.liferay.portal.SystemException {
		return getPersistence().update(assetCategory);
	}

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param        assetCategory the entity to add, update, or merge
	 * @param        merge boolean value for whether to merge the entity. The
	 *                default value is false. Setting merge to true is more
	 *                expensive and should only be true when assetCategory is
	 *                transient. See LEP-5473 for a detailed discussion of this
	 *                method.
	 * @return        true if the portlet can be displayed via Ajax
	 */
	public static com.liferay.portlet.asset.model.AssetCategory update(
		com.liferay.portlet.asset.model.AssetCategory assetCategory,
		boolean merge) throws com.liferay.portal.SystemException {
		return getPersistence().update(assetCategory, merge);
	}

	public static com.liferay.portlet.asset.model.AssetCategory updateImpl(
		com.liferay.portlet.asset.model.AssetCategory assetCategory,
		boolean merge) throws com.liferay.portal.SystemException {
		return getPersistence().updateImpl(assetCategory, merge);
	}

	public static com.liferay.portlet.asset.model.AssetCategory findByPrimaryKey(
		long categoryId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.asset.NoSuchCategoryException {
		return getPersistence().findByPrimaryKey(categoryId);
	}

	public static com.liferay.portlet.asset.model.AssetCategory fetchByPrimaryKey(
		long categoryId) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(categoryId);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetCategory> findByParentCategoryId(
		long parentCategoryId) throws com.liferay.portal.SystemException {
		return getPersistence().findByParentCategoryId(parentCategoryId);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetCategory> findByParentCategoryId(
		long parentCategoryId, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence()
				   .findByParentCategoryId(parentCategoryId, start, end);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetCategory> findByParentCategoryId(
		long parentCategoryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence()
				   .findByParentCategoryId(parentCategoryId, start, end, obc);
	}

	public static com.liferay.portlet.asset.model.AssetCategory findByParentCategoryId_First(
		long parentCategoryId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.asset.NoSuchCategoryException {
		return getPersistence()
				   .findByParentCategoryId_First(parentCategoryId, obc);
	}

	public static com.liferay.portlet.asset.model.AssetCategory findByParentCategoryId_Last(
		long parentCategoryId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.asset.NoSuchCategoryException {
		return getPersistence()
				   .findByParentCategoryId_Last(parentCategoryId, obc);
	}

	public static com.liferay.portlet.asset.model.AssetCategory[] findByParentCategoryId_PrevAndNext(
		long categoryId, long parentCategoryId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.asset.NoSuchCategoryException {
		return getPersistence()
				   .findByParentCategoryId_PrevAndNext(categoryId,
			parentCategoryId, obc);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetCategory> findByVocabularyId(
		long vocabularyId) throws com.liferay.portal.SystemException {
		return getPersistence().findByVocabularyId(vocabularyId);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetCategory> findByVocabularyId(
		long vocabularyId, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByVocabularyId(vocabularyId, start, end);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetCategory> findByVocabularyId(
		long vocabularyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByVocabularyId(vocabularyId, start, end, obc);
	}

	public static com.liferay.portlet.asset.model.AssetCategory findByVocabularyId_First(
		long vocabularyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.asset.NoSuchCategoryException {
		return getPersistence().findByVocabularyId_First(vocabularyId, obc);
	}

	public static com.liferay.portlet.asset.model.AssetCategory findByVocabularyId_Last(
		long vocabularyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.asset.NoSuchCategoryException {
		return getPersistence().findByVocabularyId_Last(vocabularyId, obc);
	}

	public static com.liferay.portlet.asset.model.AssetCategory[] findByVocabularyId_PrevAndNext(
		long categoryId, long vocabularyId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.asset.NoSuchCategoryException {
		return getPersistence()
				   .findByVocabularyId_PrevAndNext(categoryId, vocabularyId, obc);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetCategory> findByP_N(
		long parentCategoryId, java.lang.String name)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByP_N(parentCategoryId, name);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetCategory> findByP_N(
		long parentCategoryId, java.lang.String name, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByP_N(parentCategoryId, name, start, end);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetCategory> findByP_N(
		long parentCategoryId, java.lang.String name, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence()
				   .findByP_N(parentCategoryId, name, start, end, obc);
	}

	public static com.liferay.portlet.asset.model.AssetCategory findByP_N_First(
		long parentCategoryId, java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.asset.NoSuchCategoryException {
		return getPersistence().findByP_N_First(parentCategoryId, name, obc);
	}

	public static com.liferay.portlet.asset.model.AssetCategory findByP_N_Last(
		long parentCategoryId, java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.asset.NoSuchCategoryException {
		return getPersistence().findByP_N_Last(parentCategoryId, name, obc);
	}

	public static com.liferay.portlet.asset.model.AssetCategory[] findByP_N_PrevAndNext(
		long categoryId, long parentCategoryId, java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.asset.NoSuchCategoryException {
		return getPersistence()
				   .findByP_N_PrevAndNext(categoryId, parentCategoryId, name,
			obc);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetCategory> findByP_V(
		long parentCategoryId, long vocabularyId)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByP_V(parentCategoryId, vocabularyId);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetCategory> findByP_V(
		long parentCategoryId, long vocabularyId, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence()
				   .findByP_V(parentCategoryId, vocabularyId, start, end);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetCategory> findByP_V(
		long parentCategoryId, long vocabularyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence()
				   .findByP_V(parentCategoryId, vocabularyId, start, end, obc);
	}

	public static com.liferay.portlet.asset.model.AssetCategory findByP_V_First(
		long parentCategoryId, long vocabularyId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.asset.NoSuchCategoryException {
		return getPersistence()
				   .findByP_V_First(parentCategoryId, vocabularyId, obc);
	}

	public static com.liferay.portlet.asset.model.AssetCategory findByP_V_Last(
		long parentCategoryId, long vocabularyId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.asset.NoSuchCategoryException {
		return getPersistence()
				   .findByP_V_Last(parentCategoryId, vocabularyId, obc);
	}

	public static com.liferay.portlet.asset.model.AssetCategory[] findByP_V_PrevAndNext(
		long categoryId, long parentCategoryId, long vocabularyId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.asset.NoSuchCategoryException {
		return getPersistence()
				   .findByP_V_PrevAndNext(categoryId, parentCategoryId,
			vocabularyId, obc);
	}

	public static java.util.List<Object> findWithDynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	public static java.util.List<Object> findWithDynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetCategory> findAll()
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetCategory> findAll(
		int start, int end) throws com.liferay.portal.SystemException {
		return getPersistence().findAll(start, end);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetCategory> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll(start, end, obc);
	}

	public static void removeByParentCategoryId(long parentCategoryId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByParentCategoryId(parentCategoryId);
	}

	public static void removeByVocabularyId(long vocabularyId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByVocabularyId(vocabularyId);
	}

	public static void removeByP_N(long parentCategoryId, java.lang.String name)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByP_N(parentCategoryId, name);
	}

	public static void removeByP_V(long parentCategoryId, long vocabularyId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByP_V(parentCategoryId, vocabularyId);
	}

	public static void removeAll() throws com.liferay.portal.SystemException {
		getPersistence().removeAll();
	}

	public static int countByParentCategoryId(long parentCategoryId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByParentCategoryId(parentCategoryId);
	}

	public static int countByVocabularyId(long vocabularyId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByVocabularyId(vocabularyId);
	}

	public static int countByP_N(long parentCategoryId, java.lang.String name)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByP_N(parentCategoryId, name);
	}

	public static int countByP_V(long parentCategoryId, long vocabularyId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByP_V(parentCategoryId, vocabularyId);
	}

	public static int countAll() throws com.liferay.portal.SystemException {
		return getPersistence().countAll();
	}

	public static java.util.List<com.liferay.portlet.tags.model.TagsAsset> getTagsAssets(
		long pk) throws com.liferay.portal.SystemException {
		return getPersistence().getTagsAssets(pk);
	}

	public static java.util.List<com.liferay.portlet.tags.model.TagsAsset> getTagsAssets(
		long pk, int start, int end) throws com.liferay.portal.SystemException {
		return getPersistence().getTagsAssets(pk, start, end);
	}

	public static java.util.List<com.liferay.portlet.tags.model.TagsAsset> getTagsAssets(
		long pk, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().getTagsAssets(pk, start, end, obc);
	}

	public static int getTagsAssetsSize(long pk)
		throws com.liferay.portal.SystemException {
		return getPersistence().getTagsAssetsSize(pk);
	}

	public static boolean containsTagsAsset(long pk, long tagsAssetPK)
		throws com.liferay.portal.SystemException {
		return getPersistence().containsTagsAsset(pk, tagsAssetPK);
	}

	public static boolean containsTagsAssets(long pk)
		throws com.liferay.portal.SystemException {
		return getPersistence().containsTagsAssets(pk);
	}

	public static void addTagsAsset(long pk, long tagsAssetPK)
		throws com.liferay.portal.SystemException {
		getPersistence().addTagsAsset(pk, tagsAssetPK);
	}

	public static void addTagsAsset(long pk,
		com.liferay.portlet.tags.model.TagsAsset tagsAsset)
		throws com.liferay.portal.SystemException {
		getPersistence().addTagsAsset(pk, tagsAsset);
	}

	public static void addTagsAssets(long pk, long[] tagsAssetPKs)
		throws com.liferay.portal.SystemException {
		getPersistence().addTagsAssets(pk, tagsAssetPKs);
	}

	public static void addTagsAssets(long pk,
		java.util.List<com.liferay.portlet.tags.model.TagsAsset> tagsAssets)
		throws com.liferay.portal.SystemException {
		getPersistence().addTagsAssets(pk, tagsAssets);
	}

	public static void clearTagsAssets(long pk)
		throws com.liferay.portal.SystemException {
		getPersistence().clearTagsAssets(pk);
	}

	public static void removeTagsAsset(long pk, long tagsAssetPK)
		throws com.liferay.portal.SystemException {
		getPersistence().removeTagsAsset(pk, tagsAssetPK);
	}

	public static void removeTagsAsset(long pk,
		com.liferay.portlet.tags.model.TagsAsset tagsAsset)
		throws com.liferay.portal.SystemException {
		getPersistence().removeTagsAsset(pk, tagsAsset);
	}

	public static void removeTagsAssets(long pk, long[] tagsAssetPKs)
		throws com.liferay.portal.SystemException {
		getPersistence().removeTagsAssets(pk, tagsAssetPKs);
	}

	public static void removeTagsAssets(long pk,
		java.util.List<com.liferay.portlet.tags.model.TagsAsset> tagsAssets)
		throws com.liferay.portal.SystemException {
		getPersistence().removeTagsAssets(pk, tagsAssets);
	}

	public static void setTagsAssets(long pk, long[] tagsAssetPKs)
		throws com.liferay.portal.SystemException {
		getPersistence().setTagsAssets(pk, tagsAssetPKs);
	}

	public static void setTagsAssets(long pk,
		java.util.List<com.liferay.portlet.tags.model.TagsAsset> tagsAssets)
		throws com.liferay.portal.SystemException {
		getPersistence().setTagsAssets(pk, tagsAssets);
	}

	public static AssetCategoryPersistence getPersistence() {
		return _persistence;
	}

	public void setPersistence(AssetCategoryPersistence persistence) {
		_persistence = persistence;
	}

	private static AssetCategoryPersistence _persistence;
}