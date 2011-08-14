package lu.albert.android.dndcharsheet;

import lu.albert.android.dndcharsheet.fragment.Dashboard;
import lu.albert.android.dndcharsheet.fragment.Skills;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class InfoSections extends ListFragment {

	private final static String[] SECTIONS = new String[] { "Dashboard",
			"Skills", "Inventory", "Spells", "Character Details" };

	public static final int IDX_DASHBOARD = 0;
	public static final int IDX_SKILLS = 1;
	public static final int IDX_INVENTORY = 2;
	public static final int IDX_SPELLS = 3;
	public static final int IDX_DETAILS = 4;

	public static final String STATE_CURRENT_SECTION = "currentSection";

	boolean mDualPane;
	int mCurrentSection = 0;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		setListAdapter(new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_checked, SECTIONS));

		View detailsFrame = getActivity().findViewById(R.id.details);
		mDualPane = detailsFrame != null
				&& detailsFrame.getVisibility() == View.VISIBLE;

		if (savedInstanceState != null) {
			// Restore last checked position
			mCurrentSection = savedInstanceState.getInt(STATE_CURRENT_SECTION,
					0);
		}

		if (mDualPane) {
			getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
			showSection(mCurrentSection);
		}

	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt(STATE_CURRENT_SECTION, mCurrentSection);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		showSection(position);
	}

	void showSection(int index) {
		mCurrentSection = index;

		if (mDualPane) {
			getListView().setItemChecked(index, true);
		} else {

		}

		switch (index) {
		default:
			Toast.makeText(getActivity(), "Selected section is not yet implemented", 5).show();
		case IDX_DASHBOARD:
			showSection(Dashboard.newInstance());
			break;
		case IDX_SKILLS:
			showSection(Skills.newInstance());
			break;
		}

	}

	void showSection(Fragment newFragment) {
		if (mDualPane) {
			FragmentTransaction ft = getFragmentManager().beginTransaction();
			ft.replace(R.id.details, newFragment);
			ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
			ft.commit();
		} else {
			// TODO
			Toast.makeText(getActivity(), "Not yet implemented as Activiy!", 5).show();
			// Intent intent = new Intent();
			// intent.setClass(getActivity(), DetailsActivity.class);
			// intent.putExtra(DndCharsheetActivity.ARG_INDEX, index);
			// startActivity(intent);
		}
	}
}
