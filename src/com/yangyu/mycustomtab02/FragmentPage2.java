package com.yangyu.mycustomtab02;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.yangyu.mycustomtab02.data.Users;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FragmentPage2 extends Fragment {

	@ViewInject(R.id.login_user_input)
	TextView login_username;

	@ViewInject(R.id.login_password_input)
	TextView login_userpasw;

	// @ViewInject(R.id.signin_button)
	// Button signin;
	
	View view;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		 view = inflater.inflate(R.layout.fragment_2, null);
		
		ViewUtils.inject(this, view);
		
		return view;

	}

	@OnClick(R.id.signin_button)
	public void OnClick(View v) {
		DbUtils db = DbUtils.create(getActivity());
		Users user = new Users();
		user.setUsername((String) login_username.getText());
		user.setUserpasw((String) login_userpasw.getText());
		
			try {
				db.save(user);
				
			} catch (DbException e) {
				e.printStackTrace();
			}
		
	}
	
	
}