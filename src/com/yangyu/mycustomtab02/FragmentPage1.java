package com.yangyu.mycustomtab02;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView.ScaleType;

public  class FragmentPage1 extends Fragment {

	private ViewPager viewPager; // android-support-v4中的滑动组件
	private List<ImageView> imageViews; // 滑动的图片集合

	private String[] titles; // 图片标题
	private int[] imageResId; // 图片ID
	private List<View> dots; // 图片标题正文的那些点

	private TextView tv_title;
	private int currentItem = 0; // 当前图片的索引号

	private GridView gridview;

	private String texts[] = null;
	private int images[] = null;

	private View view;

	// An ExecutorService that can schedule commands to run after a given delay,
	// or to execute periodically.
	private ScheduledExecutorService scheduledExecutorService;

	// 切换当前显示的图片
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			viewPager.setCurrentItem(currentItem);// 切换当前显示的图片
		};
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		view = inflater.inflate(R.layout.fragment_1, container, false);
		tv_title = (TextView) view.findViewById(R.id.tv_title);
		viewPager = (ViewPager) view.findViewById(R.id.vp);
		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();

			imageResId = new int[] { R.drawable.xianjian01,
					R.drawable.xianjian01, R.drawable.xianjian01,
					R.drawable.xianjian01, R.drawable.xianjian01 };
			titles = new String[imageResId.length];
			titles[0] = "xx美容1";
			titles[1] = "xx美容2";
			titles[2] = "xx美容3";
			titles[3] = "xx美容4";
			titles[4] = "xx美容5";

			imageViews = new ArrayList<ImageView>();

			// 初始化图片资源
			for (int i = 0; i < imageResId.length; i++) {
				ImageView imageView = new ImageView(getActivity());
				imageView.setImageResource(imageResId[i]);
				imageView.setScaleType(ScaleType.CENTER_CROP);
				imageViews.add(imageView);
			}

			dots = new ArrayList<View>();
			dots.add(view.findViewById(R.id.v_dot0));
			dots.add(view.findViewById(R.id.v_dot1));
			dots.add(view.findViewById(R.id.v_dot2));
			dots.add(view.findViewById(R.id.v_dot3));
			dots.add(view.findViewById(R.id.v_dot4));

			tv_title.setText(titles[0]);

			viewPager.setAdapter(new MyAdapter());// 设置填充ViewPager页面的适配器
			// 设置一个监听器，当ViewPager中的页面改变时调用
			viewPager.setOnPageChangeListener(new MyPageChangeListener());

			images = new int[] { R.drawable.xianjian01, R.drawable.xianjian01,
					R.drawable.xianjian01, R.drawable.xianjian01,
					R.drawable.xianjian01 };
			texts = new String[] { "xxx", "xxx", "xxx", "xxx", "xxx" };

			gridview = (GridView) view.findViewById(R.id.gridview);
			ArrayList<HashMap<String, Object>> lstImageItem = new ArrayList<HashMap<String, Object>>();
			for (int i = 0; i < 5; i++) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("itemImage", images[i]);
				map.put("itemText", texts[i]);
				lstImageItem.add(map);
			}

			SimpleAdapter saImageItems = new SimpleAdapter(getActivity(),
					lstImageItem, R.layout.item, new String[] { "itemImage",
							"itemText" }, new int[] { R.id.iv_item,
							R.id.tv_name });
			gridview.setAdapter(saImageItems);
			gridview.setOnItemClickListener(new ItemClickListener());

		}

		return view;

	}

	class ItemClickListener implements OnItemClickListener {

		public void onItemClick(AdapterView<?> parent, View view, int position,
				long rowid) {
			HashMap<String, Object> item = (HashMap<String, Object>) parent
					.getItemAtPosition(position);
			// 获取数据源的属性值
			String itemText = (String) item.get("itemText");
			Object object = item.get("itemImage");
			Toast.makeText(getActivity(), itemText, Toast.LENGTH_LONG).show();
			Intent intent = new Intent();
			intent.setClass(getActivity(), TestActivity.class);
			intent.putExtra("chose", texts[position]);

			// 根据图片进行相应的跳转
			switch (images[position]) {
			case R.drawable.xianjian01:

				startActivity(intent);// 启动另一个Activity

				break;
			case R.drawable.xianjian02:

				startActivity(intent);

				break;
			case R.drawable.xianjian03:

				startActivity(intent);

				break;

			case R.drawable.xianjian04:

				startActivity(intent);

				break;

			case R.drawable.icon_home_nor:

				startActivity(intent);

				break;

			}

		}
	}

	@Override
	public void onStart() {
		scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
		// 当Activity显示出来后，每两秒钟切换一次图片显示
		scheduledExecutorService.scheduleAtFixedRate(new ScrollTask(), 1, 2,
				TimeUnit.SECONDS);
		super.onStart();
	}

	@Override
	public void onStop() {
		// 当Activity不可见的时候停止切换
		scheduledExecutorService.shutdown();
		super.onStop();
	}

	/**
	 * 换行切换任务
	 * 
	 * @author Administrator
	 * 
	 */
	private class ScrollTask implements Runnable {

		public void run() {
			synchronized (viewPager) {
				System.out.println("currentItem: " + currentItem);
				currentItem = (currentItem + 1) % imageViews.size();
				handler.obtainMessage().sendToTarget(); // 通过Handler切换图片
			}
		}

	}

	/**
	 * 当ViewPager中页面的状态发生改变时调用
	 * 
	 * @author Administrator
	 * 
	 */
	private class MyPageChangeListener implements OnPageChangeListener {
		private int oldPosition = 0;

		/**
		 * This method will be invoked when a new page becomes selected.
		 * position: Position index of the new selected page.
		 */
		public void onPageSelected(int position) {
			currentItem = position;
			tv_title.setText(titles[position]);
			dots.get(oldPosition).setBackgroundResource(R.drawable.dot_normal);
			dots.get(position).setBackgroundResource(R.drawable.dot_focused);
			oldPosition = position;
		}

		public void onPageScrollStateChanged(int arg0) {

		}

		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}
	}

	/**
	 * 填充ViewPager页面的适配器
	 * 
	 * @author Administrator
	 * 
	 */
	private class MyAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return imageResId.length;
		}

		@Override
		public Object instantiateItem(View arg0, int arg1) {
			((ViewPager) arg0).addView(imageViews.get(arg1));
			return imageViews.get(arg1);
		}

		@Override
		public void destroyItem(View arg0, int arg1, Object arg2) {
			((ViewPager) arg0).removeView((View) arg2);
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1) {

		}

		@Override
		public Parcelable saveState() {
			return null;
		}

		@Override
		public void startUpdate(View arg0) {

		}

		@Override
		public void finishUpdate(View arg0) {

		}
	}

	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_carousel, container,
					false);
			return rootView;
		}
	}
}
