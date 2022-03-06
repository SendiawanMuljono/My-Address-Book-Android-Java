package com.uas.myaddressbook.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.uas.myaddressbook.R;

public class SearchFragment extends Fragment {

    EditText etSearch;
    TextView tvSearchClick;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        etSearch = (EditText)view.findViewById(R.id.etSearch);
        tvSearchClick = (TextView)view.findViewById(R.id.tvSearchClick);

        tvSearchClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!etSearch.getText().equals("")){
                    Bundle bundle = new Bundle();
                    bundle.putString("search", etSearch.getText().toString());
                    ListEmployeeFragment fragment = new ListEmployeeFragment();
                    fragment.setArguments(bundle);
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.containerListEmployee, fragment)
                            .addToBackStack(fragment.getClass().getSimpleName())
                            .commit();
                }
            }
        });

        return view;
    }

}