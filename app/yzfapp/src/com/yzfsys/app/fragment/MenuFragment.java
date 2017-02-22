package com.yzfsys.app.fragment;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.yzfsys.app.R;
import com.yzfsys.app.activity.MainActivity;
import com.yzfsys.app.adapter.MenuAdapter;

public class MenuFragment extends Fragment {

	private ListView mLvMenu;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_menu_list, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mLvMenu = (ListView)getActivity().findViewById(R.id.lv_ml_list_menu);
//		String[] menuItems = getResources().getStringArray(R.array.menu_titles);
		ArrayList<String> titles = new ArrayList<String>();
		titles.add("订单管理");
		titles.add("待办事项");
//		titles.add("客户服务");
		titles.add("联系我们");
		MenuAdapter adapter = new MenuAdapter(getActivity(), 0, titles);
		mLvMenu.setAdapter(adapter);
		mLvMenu.setOnItemClickListener(createLvItemClickListener());
	}

	private AdapterView.OnItemClickListener createLvItemClickListener() {
		return new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, 
					int position, long id) {
				
				if (view == null)
					return;

				Fragment newContent = null;
				switch (position) {
				case 0:
					newContent = new OrderListFragment();
					break;
				case 1:
					newContent = new WorkListFragment();
					break;
				case 2:
					newContent = new AboutFragment();
					break;
				case 3:
					break;
				}
				if (newContent != null)
					switchFragment(newContent);
			}
		};
	}
	
	// the meat of switching the above fragment
	private void switchFragment(Fragment fragment) {
		if (getActivity() == null)
			return;
		
		if (getActivity() instanceof MainActivity) {
			MainActivity fca = (MainActivity) getActivity();
			fca.switchContent(fragment);
		}
	}


}
