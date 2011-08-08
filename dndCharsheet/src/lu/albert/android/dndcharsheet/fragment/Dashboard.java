package lu.albert.android.dndcharsheet.fragment;

import lu.albert.android.dndcharsheet.DndCharsheetActivity;
import lu.albert.android.dndcharsheet.R;
import lu.albert.android.dndcharsheet.util.WeaponAdapter;
import lu.albert.d20character.api.Character;
import lu.albert.d20character.api.Character.DamageType;
import android.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Dashboard extends Fragment {

	// TODO this could be moved to the settings
	private static final String SPEED_UNIT = "ft";

	DndCharsheetActivity mMainActivity;
	Character mChar;
	private EditText mHitPoints;
	private TextView mInitiative;
	private EditText mTmpHitPoints;
	private EditText mNonlethal;
	private TextView mArmorClass;
	private TextView mArmorClassTouch;
	private TextView mArmorClassFF;
	private Button mBtnHpMinus5;
	private Button mBtnHpMinus1;
	private Button mBtnHpPlus1;
	private Button mBtnHpPlus5;
	private Button mBtnHealAllHp;
	private Button mBtnTmpHpMinus5;
	private Button mBtnTmpHpMinus1;
	private Button mBtnTmpHpPlus1;
	private Button mBtnTmpHpPlus5;
	private Button mBtnRemoveAllTmpHp;
	private Button mBtnNonlethalDamageMinus5;
	private Button mBtnNonlethalDamageMinus1;
	private Button mBtnNonlethalDamagePlus1;
	private Button mBtnNonlethalDamagePlus5;
	private Button mBtnHealAllNonlethal;
	private TextView mTxtWillSave;
	private TextView mTxtReflexSave;
	private TextView mTxtFortSave;
	private EditText mEddTmpWillSave;
	private EditText mEddTmpReflexSave;
	private EditText mEddTmpFortSave;
	private TextView mDamageReduction;
	private TextView mTxtSpeed;

	private ListView mLstWeapons;

	public static Dashboard newInstance() {
		Dashboard f = new Dashboard();
		return f;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (container == null) {
			return null;
		}
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.dashboard, container, false);
	}

	@Override
	public void onResume() {
		super.onResume();
		mMainActivity = (DndCharsheetActivity) getActivity();
		mChar = mMainActivity.getCharacter();
		mHitPoints = (EditText) getActivity().findViewById(R.id.edtHp);
		mInitiative = (TextView) getActivity().findViewById(R.id.txtInitiative);
		mTmpHitPoints = (EditText) getActivity().findViewById(R.id.edtTmpHp);
		mNonlethal = (EditText) getActivity().findViewById(
				R.id.edtNonlethalDamage);
		mArmorClass = (TextView) getActivity().findViewById(R.id.txtAC);
		mDamageReduction = (TextView) getActivity().findViewById(
				R.id.txtDamageReduction);
		mArmorClassTouch = (TextView) getActivity().findViewById(
				R.id.txtTouchAC);
		mArmorClassFF = (TextView) getActivity().findViewById(R.id.txtFFAc);

		mBtnHpMinus5 = (Button) mMainActivity.findViewById(R.id.btnHpMinus5);
		mBtnHpMinus1 = (Button) mMainActivity.findViewById(R.id.btnHpMinus1);
		mBtnHpPlus1 = (Button) mMainActivity.findViewById(R.id.btnHpPlus1);
		mBtnHpPlus5 = (Button) mMainActivity.findViewById(R.id.btnHpPlus5);
		mBtnHealAllHp = (Button) mMainActivity.findViewById(R.id.btnHealAllHp);

		mBtnTmpHpMinus5 = (Button) mMainActivity
				.findViewById(R.id.btnTmpHpMinus5);
		mBtnTmpHpMinus1 = (Button) mMainActivity
				.findViewById(R.id.btnTmpHpMinus1);
		mBtnTmpHpPlus1 = (Button) mMainActivity
				.findViewById(R.id.btnTmpHpPlus1);
		mBtnTmpHpPlus5 = (Button) mMainActivity
				.findViewById(R.id.btnTmpHpPlus5);
		mBtnRemoveAllTmpHp = (Button) mMainActivity
				.findViewById(R.id.btnRemoveAllTmpHp);

		mBtnNonlethalDamageMinus5 = (Button) mMainActivity
				.findViewById(R.id.btnNonlethalDamageMinus5);
		mBtnNonlethalDamageMinus1 = (Button) mMainActivity
				.findViewById(R.id.btnNonlethalDamageMinus1);
		mBtnNonlethalDamagePlus1 = (Button) mMainActivity
				.findViewById(R.id.btnNonlethalDamagePlus1);
		mBtnNonlethalDamagePlus5 = (Button) mMainActivity
				.findViewById(R.id.btnNonlethalDamagePlus5);
		mBtnHealAllNonlethal = (Button) mMainActivity
				.findViewById(R.id.btnHealAllNonlethal);

		mTxtFortSave = (TextView) mMainActivity.findViewById(R.id.txtFortSave);
		mTxtReflexSave = (TextView) mMainActivity
				.findViewById(R.id.txtReflexSave);
		mTxtWillSave = (TextView) mMainActivity.findViewById(R.id.txtWillSave);
		mEddTmpFortSave = (EditText) mMainActivity
				.findViewById(R.id.edtTempFort);
		mEddTmpReflexSave = (EditText) mMainActivity
				.findViewById(R.id.edtTempReflex);
		mEddTmpWillSave = (EditText) mMainActivity
				.findViewById(R.id.edtTempWill);
		mTxtSpeed = (TextView) mMainActivity.findViewById(R.id.txtSpeed);
		mLstWeapons = (ListView) mMainActivity.findViewById(R.id.lstWeapons);

		updateValues();

		attachListeners();
	}

	private void attachListeners() {

		//
		// Lethal damage
		//
		mBtnHpMinus5.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mChar.addDamage(5);
				updateValues();
			}
		});
		mBtnHpMinus1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mChar.addDamage(1);
				updateValues();
			}
		});
		mBtnHpPlus1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mChar.removeDamage(1);
				updateValues();
			}
		});
		mBtnHpPlus5.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mChar.removeDamage(5);
				updateValues();
			}
		});
		mBtnHealAllHp.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mChar.removeAllDamage();
				updateValues();
			}
		});

		//
		// Nonlethal damage
		//
		mBtnNonlethalDamageMinus5.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mChar.removeDamage(5, DamageType.Nonlethal);
				updateValues();
			}
		});
		mBtnNonlethalDamageMinus1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mChar.removeDamage(1, DamageType.Nonlethal);
				updateValues();
			}
		});
		mBtnNonlethalDamagePlus1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mChar.addDamage(1, DamageType.Nonlethal);
				updateValues();
			}
		});
		mBtnNonlethalDamagePlus5.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mChar.addDamage(5, DamageType.Nonlethal);
				updateValues();
			}
		});
		mBtnHealAllNonlethal.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mChar.removeAllDamage(DamageType.Nonlethal);
				updateValues();
			}
		});

		//
		// Temporary Hitpoints
		//
		mBtnTmpHpMinus5.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mChar.removeTmpHp(5);
				updateValues();
			}
		});
		mBtnTmpHpMinus1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mChar.removeTmpHp(1);
				updateValues();
			}
		});
		mBtnTmpHpPlus1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mChar.addTmpHp(1);
				updateValues();
			}
		});
		mBtnTmpHpPlus5.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mChar.addTmpHp(5);
				updateValues();
			}
		});
		mBtnRemoveAllTmpHp.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mChar.removeAllTmpHp();
				updateValues();
			}
		});

		//
		// Temporary Saving Throws
		//
		mEddTmpFortSave.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// nothing to do
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// nothing to do
			}

			@Override
			public void afterTextChanged(Editable s) {
				int newValue = 0;
				if (!"".equals(s.toString())) {
					try {
						newValue = Integer.parseInt(s.toString());
					} catch (NumberFormatException exc) {
						Toast.makeText(getActivity(), String.format(
								"Unable to convert %s to a number", s), 5);
						return;
					}
				}
				mChar.setTmpFortitudeSave(newValue);
				updateValues();
			}
		});
		mEddTmpReflexSave.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// nothing to do
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// nothing to do
			}

			@Override
			public void afterTextChanged(Editable s) {
				int newValue = 0;
				if (!"".equals(s.toString())) {
					try {
						newValue = Integer.parseInt(s.toString());
					} catch (NumberFormatException exc) {
						Toast.makeText(getActivity(), String.format(
								"Unable to convert %s to a number", s), 5);
						return;
					}
				}
				mChar.setTmpReflexSave(newValue);
				updateValues();
			}
		});
		mEddTmpWillSave.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// nothing to do
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// nothing to do
			}

			@Override
			public void afterTextChanged(Editable s) {
				int newValue = 0;
				if (!"".equals(s.toString())) {
					try {
						newValue = Integer.parseInt(s.toString());
					} catch (NumberFormatException exc) {
						Toast.makeText(getActivity(), String.format(
								"Unable to convert %s to a number", s), 5);
						return;
					}
				}
				mChar.setTmpWillSave(newValue);
				updateValues();
			}
		});
	}

	private void updateValues() {

		// values
		mHitPoints.setText(String.format("%d", mChar.getCurrentHitpoints()));
		mInitiative.setText(String.format("%+d", mChar.getInitiative()));
		mTmpHitPoints.setText(String.format("%d", mChar.getTmpHitpoints()));
		mNonlethal.setText(String.format("%d", mChar.getNonlethalDamage()));
		mArmorClass.setText(String.format("%+d", mChar.getTotalArmorClass()));
		mArmorClassTouch.setText(String.format("%+d", mChar.getTouchAC()));
		mArmorClassFF.setText(String.format("%+d", mChar.getFlatFootedAC()));
		mTxtFortSave.setText(String.format("%+d", mChar.getFortitudeSave()));
		mTxtReflexSave.setText(String.format("%+d", mChar.getReflexSave()));
		mTxtWillSave.setText(String.format("%+d", mChar.getWillSave()));
		mDamageReduction.setText(mChar.getDamageReduction());
		mTxtSpeed.setText(String.format("%d%s", mChar.getSpeed(), SPEED_UNIT));
		mLstWeapons.setAdapter(new WeaponAdapter(mMainActivity, mChar));
	}

}
