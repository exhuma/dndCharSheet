package lu.albert.android.dndcharsheet.fragment;

import lu.albert.android.dndcharsheet.DndCharsheetActivity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

public class DetailsFragment extends Fragment {
	
	public static DetailsFragment newInstance(int index){
		DetailsFragment f = new DetailsFragment();
		Bundle args = new Bundle();
		args.putInt(DndCharsheetActivity.ARG_INDEX, index);
		f.setArguments(args);
		return f;
	}
	
	public int getShownIndex(){
		return getArguments().getInt(DndCharsheetActivity.ARG_INDEX, 0);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (container == null){
			return null;
		}
		ScrollView scroller = new ScrollView(getActivity());
		TextView text = new TextView(getActivity());
		int padding = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getActivity().getResources().getDisplayMetrics());
		text.setPadding(padding, padding, padding, padding);
		scroller.addView(text);
		text.setText(String.format("Section %d", getShownIndex()));
		return scroller;
		
	}
	
}
