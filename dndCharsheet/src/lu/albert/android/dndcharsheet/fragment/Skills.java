package lu.albert.android.dndcharsheet.fragment;

import lu.albert.android.dndcharsheet.DndCharsheetActivity;
import lu.albert.android.dndcharsheet.R;
import lu.albert.d20character.api.Character;
import lu.albert.d20character.api.Character.Ability;
import lu.albert.d20character.api.Skill;
import android.app.Fragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class Skills extends Fragment {

	private ArrayAdapter<Ability> mAdapter;
	private final int LR_PAD = 20;
	private final int TB_PAD = 8;

	public static Skills newInstance() {
		Skills f = new Skills();
		return f;
	}

	private Character mLoadedCharacter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (container == null) {
			return null;
		}

		DndCharsheetActivity activity = (DndCharsheetActivity) getActivity();
		this.mLoadedCharacter = activity.getCharacter();
		mAdapter = new ArrayAdapter<Ability>(getActivity(),
				android.R.layout.simple_spinner_item);
		mAdapter.addAll(Ability.values());
		mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.skills, container, false);

	}

	@Override
	public void onResume() {
		super.onResume();
		TableLayout tblSkills = (TableLayout) getActivity().findViewById(
				R.id.tblSkills);

		// TODO: This is sub-optimal. This should only redraw the skills instead
		// of re-adding them
		for (int i = 0; i < tblSkills.getChildCount(); i++) {
			tblSkills.removeViewAt(i);
		}

		addHeader(tblSkills);
		for (Skill skill : mLoadedCharacter.getSkills()) {
			addSkill(skill, tblSkills);
		}

	}

	public void addSkill(final Skill skill, TableLayout table) {
		TableRow row = new TableRow(getActivity());

		// widgets
		final EditText name = new EditText(getActivity());
		name.clearFocus();
		
		final Spinner ability = new Spinner(getActivity());
		ability.clearFocus();
		
		final TextView modifier = new TextView(getActivity());
		modifier.setTypeface(Typeface.DEFAULT_BOLD);
		modifier.setGravity(Gravity.RIGHT);
		
		final TextView abModifier = new TextView(getActivity());
		abModifier.setGravity(Gravity.RIGHT);
		
		final EditText ranks = new EditText(getActivity());
		ranks.clearFocus();
		ranks.setInputType(InputType.TYPE_CLASS_NUMBER);
		ranks.setGravity(Gravity.RIGHT);
		
		final EditText misc = new EditText(getActivity());
		misc.setInputType(InputType.TYPE_CLASS_NUMBER);
		misc.clearFocus();
		misc.setGravity(Gravity.RIGHT);

		final CheckBox untrained = new CheckBox(getActivity());
		final CheckBox penalty1 = new CheckBox(getActivity());
		final CheckBox penalty2 = new CheckBox(getActivity());

		// values
		name.setText(skill.getName());
		ability.setAdapter(mAdapter);
		for (int i = 0; i < mAdapter.getCount(); i++) {
			if (mAdapter.getItem(i).equals(skill.getKeyAbility())) {
				ability.setSelection(i);
				break;
			}
		}

		modifier.setText(String.format("%d",
				mLoadedCharacter.getSkillModifier(skill)));
		abModifier.setText(String.format("%d",
				mLoadedCharacter.getAbilityModifier(skill.getKeyAbility())));
		ranks.setText(String.format("%d", skill.getRanks()));
		misc.setText(String.format("%d", skill.getMiscModifier()));
		untrained.setChecked(skill.isUntrained());
		penalty1.setChecked(skill.getPenaltyMultiplier() >= 1);
		penalty2.setChecked(skill.getPenaltyMultiplier() >= 2);

		// add widgets to row
		row.addView(name);
		row.addView(ability);
		row.addView(modifier);
		row.addView(abModifier);
		row.addView(ranks);
		row.addView(misc);
		row.addView(untrained);
		row.addView(penalty1);
		row.addView(penalty2);

		// listeners
		name.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// do nothing
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// do nothing
			}

			@Override
			public void afterTextChanged(Editable s) {
				skill.setName(s.toString());
			}
		});
		
		ranks.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// do nothing
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// do nothing
			}

			@Override
			public void afterTextChanged(Editable s) {
				if (s == null || "" == s.toString()) {
					return;
				}
				int new_value;
				try {
					new_value = Integer.parseInt(s.toString());
				} catch (NumberFormatException exc) {
					Log.w(DndCharsheetActivity.TAG,
							String.format("Unable to convert %s to an int!", s));
					return;
				}
				skill.setRanks(new_value);
				modifier.setText(String.format("%d",
						mLoadedCharacter.getSkillModifier(skill)));
			}
		});
		
		misc.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// do nothing
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// do nothing
			}

			@Override
			public void afterTextChanged(Editable s) {
				if (s == null || "" == s.toString()) {
					return;
				}
				int new_value;
				try {
					new_value = Integer.parseInt(s.toString());
				} catch (NumberFormatException exc) {
					Log.w(DndCharsheetActivity.TAG,
							String.format("Unable to convert %s to an int!", s));
					return;
				}
				skill.setMiscModifier(new_value);
				modifier.setText(String.format("%d",
						mLoadedCharacter.getSkillModifier(skill)));
			}
		});
		
		ability.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int pos, long id) {
				Ability new_value = (Ability) parent.getItemAtPosition(pos);
				skill.setKeyAbility(new_value);
				modifier.setText(String.format("%d",
						mLoadedCharacter.getSkillModifier(skill)));
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// do nothing
			}
		});

		untrained.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				skill.setUntrained(isChecked);
				
			}
		});
		
		penalty1.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					skill.setPenaltyMultiplier(1);
				} else {
					penalty2.setChecked(false);
					skill.setPenaltyMultiplier(0);
				}
				modifier.setText(String.format("%d",
						mLoadedCharacter.getSkillModifier(skill)));
			}
		});

		penalty2.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked){
					penalty1.setChecked(true);
					skill.setPenaltyMultiplier(2);
				} else if (penalty1.isChecked()){
					skill.setPenaltyMultiplier(1);
				} else {
					skill.setPenaltyMultiplier(0);
				}
				modifier.setText(String.format("%d",
						mLoadedCharacter.getSkillModifier(skill)));
			}
		});

		name.setPadding(LR_PAD, TB_PAD, LR_PAD, TB_PAD);
		ability.setPadding(LR_PAD, TB_PAD, LR_PAD, TB_PAD);
		modifier.setPadding(LR_PAD, TB_PAD, LR_PAD, TB_PAD);
		abModifier.setPadding(LR_PAD, TB_PAD, LR_PAD, TB_PAD);
		ranks.setPadding(LR_PAD, TB_PAD, LR_PAD, TB_PAD);
		misc.setPadding(LR_PAD, TB_PAD, LR_PAD, TB_PAD);
		untrained.setPadding(LR_PAD, TB_PAD, LR_PAD, TB_PAD);
		penalty1.setPadding(LR_PAD/2, TB_PAD, LR_PAD/2, TB_PAD);
		penalty2.setPadding(LR_PAD/2, TB_PAD, LR_PAD/2, TB_PAD);
		
		table.addView(row);

	}

	void addHeader(TableLayout table) {
		TableRow header = new TableRow(getActivity());

		TextView name = new TextView(getActivity());
		TextView ability = new TextView(getActivity());
		TextView modifier = new TextView(getActivity());
		TextView abModifier = new TextView(getActivity());
		TextView ranks = new TextView(getActivity());
		TextView misc = new TextView(getActivity());
		TextView untrained = new TextView(getActivity());
		TextView acp1 = new TextView(getActivity());
		TextView acp2 = new TextView(getActivity());

		name.setText(R.string.skill);
		ability.setText(R.string.skill_ability);
		modifier.setText(R.string.skill_modifier);
		abModifier.setText(R.string.ability_modifier);
		ranks.setText(R.string.skill_ranks);
		misc.setText(R.string.skill_misc);
		untrained.setText(R.string.skill_untrained);
		acp1.setText(R.string.skill_acp1);
		acp2.setText(R.string.skill_acp2);

		header.addView(name);
		header.addView(ability);
		header.addView(modifier);
		header.addView(abModifier);
		header.addView(ranks);
		header.addView(misc);
		header.addView(untrained);
		header.addView(acp1);
		header.addView(acp2);

		int LR_PAD = 20;
		int TB_PAD = 8;
		name.setPadding(LR_PAD, TB_PAD, LR_PAD, TB_PAD);
		ability.setPadding(LR_PAD, TB_PAD, LR_PAD, TB_PAD);
		modifier.setPadding(LR_PAD, TB_PAD, LR_PAD, TB_PAD);
		abModifier.setPadding(LR_PAD, TB_PAD, LR_PAD, TB_PAD);
		ranks.setPadding(LR_PAD, TB_PAD, LR_PAD, TB_PAD);
		misc.setPadding(LR_PAD, TB_PAD, LR_PAD, TB_PAD);
		untrained.setPadding(LR_PAD, TB_PAD, LR_PAD, TB_PAD);
		acp1.setPadding(LR_PAD/2, TB_PAD, LR_PAD/2, TB_PAD);
		acp2.setPadding(LR_PAD/2, TB_PAD, LR_PAD/2, TB_PAD);

		table.addView(header);

	}

}
