package com.excellence.basetoolslibrary.baseadapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.excellence.basetoolslibrary.baseadapter.base.ItemViewDelegate;
import com.excellence.basetoolslibrary.baseadapter.base.ItemViewDelegateManager;

import java.util.Arrays;
import java.util.List;

/**
 * <pre>
 *     author : VeiZhang
 *     blog   : https://veizhang.github.io/
 *     time   : 2017/4/19
 *     desc   : 多布局Gridview Listview通用适配器
 * </pre>
 */

public class MultiItemTypeAdapter<T> extends BaseAdapter
{
	protected Context mContext;
	protected List<T> mDatas;
	private ItemViewDelegateManager<T> mItemViewDelegateManager;

	public MultiItemTypeAdapter(Context context, T[] datas)
	{
		this(context, datas == null ? null : Arrays.asList(datas));
	}

	public MultiItemTypeAdapter(Context context, List<T> datas)
	{
		mContext = context;
		mDatas = datas;
		mItemViewDelegateManager = new ItemViewDelegateManager<>();
	}

	/**
	 * 添加视图
	 *
	 * @param delegate 视图
	 * @return
	 */
	public MultiItemTypeAdapter<T> addItemViewDelegate(ItemViewDelegate<T> delegate)
	{
		mItemViewDelegateManager.addDelegate(delegate);
		return this;
	}

	/**
	 * 添加视图
	 *
	 * @param viewType 布局类型
	 * @param delegate 视图
	 * @return
	 */
	public MultiItemTypeAdapter<T> addItemViewDelegate(int viewType, ItemViewDelegate<T> delegate)
	{
		mItemViewDelegateManager.addDelegate(viewType, delegate);
		return this;
	}

	/**
	 * 移除视图
	 *
	 * @param delegate 视图
	 * @return
	 */
	public MultiItemTypeAdapter<T> removeItemViewDelegate(ItemViewDelegate<T> delegate)
	{
		mItemViewDelegateManager.removeDelegate(delegate);
		return this;
	}

	/**
	 * 移除视图
	 *
	 * @param viewType 布局类型
	 * @return
	 */
	public MultiItemTypeAdapter<T> removeItemViewDelegate(int viewType)
	{
		mItemViewDelegateManager.removeDelegate(viewType);
		return this;
	}

	/**
	 * 判断视图是否可用
	 *
	 * @return {@code true}:是<br>{@code false}:否
	 */
	private boolean userItemViewDelegateManager()
	{
		return mItemViewDelegateManager.getItemViewDelegateCount() > 0;
	}

	/**
	 * 获取视图数量
	 *
	 * @return 视图数量
	 */
	@Override
	public int getViewTypeCount()
	{
		if (userItemViewDelegateManager())
			return mItemViewDelegateManager.getItemViewDelegateCount();
		return super.getViewTypeCount();
	}

	/**
	 * 获取视图类型
	 *
	 * @param position 位置
	 * @return 视图类型
	 */
	@Override
	public int getItemViewType(int position)
	{
		if (userItemViewDelegateManager())
		{
			return mItemViewDelegateManager.getItemViewType(mDatas.get(position), position);
		}
		return super.getItemViewType(position);
	}

	@Override
	public int getCount()
	{
		return mDatas == null ? 0 : mDatas.size();
	}

	@Override
	public T getItem(int position)
	{
		return mDatas == null ? null : mDatas.get(position);
	}

	@Override
	public long getItemId(int position)
	{
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		ItemViewDelegate<T> delegate = mItemViewDelegateManager.getItemViewDelegate(getItem(position), position);
		int layoutId = delegate.getItemViewLayoutId();
		ViewHolder viewHolder = ViewHolder.getViewHolder(mContext, convertView, parent, layoutId);
		delegate.convert(viewHolder, getItem(position), position);
		return viewHolder.getConvertView();
	}

	/**
	 * 刷新视图
	 *
	 * @param datas 数据源
	 */
	public void notifyNewData(List<T> datas)
	{
		mDatas = datas;
		notifyDataSetChanged();
	}
}
