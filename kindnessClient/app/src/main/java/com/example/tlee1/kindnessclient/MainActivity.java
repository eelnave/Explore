package com.example.tlee1.kindnessclient;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import java.util.Comparator;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}

/**Descriptor Class**/
/** A Descriptor is either a CalcDescriptor or a GroupDescriptor of Descriptors. */
abstract class Descriptor {
    private final int id, titleID, iconID;
    private final Class<? extends InfoFragment> calcClass;
    private String title;

    /**
     * Params:
     * @param id        - ID of this node. Must be unique and stable
     *                  across all releases of the app
     * @param titleID   - an ID from R.string
     * @param iconID    -
     * @param calcClass - the Java class for the calculator that
     *                  should be opened when the user clicks
     *                  this descriptor
     */
    Descriptor(int id, int titleID, int iconID,
               Class<? extends InfoFragment> calcClass) {
        this.id = id;
        this.titleID = titleID;
        this.iconID = iconID;
        this.calcClass = calcClass;
    }

    int getID() {
        return id;
    }

    int getIconID() {
        return iconID;
    }

    Class<? extends InfoFragment> getCalcClass() {
        return calcClass;
    }

    String getTitle(Resources res) {
        if (title == null) {
            title = res.getString(titleID);
        }
        return title;
    }

    @Override
    public String toString() {
        return calcClass.getSimpleName();
    }

    static final Comparator<Descriptor> compareID = new Comparator<Descriptor>() {
        @Override
        public int compare(Descriptor ch1, Descriptor ch2) {
            return ch1.id - ch2.id;
        }
    };
}

/**CalcDescriptor**/

/**  A CalcDescriptor holds information about a calculator */
final class CalcDescriptor extends Descriptor {
    CalcDescriptor(int descripID, int titleID, int iconID,
                   Class<? extends InfoFragment> calcClass) {
        super(descripID, titleID, iconID, calcClass);
    }
}

/**About Class**/
public final class About extends InfoFragment {
    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.about, container, false);
        String nameAndVersion = getString(R.string.appName) + " " + getString(R.string.versionName);
        TextView version = view.findViewById(R.id.txtVersion);
        version.setText(nameAndVersion);
        return view;
    }
}

public abstract class InfoFragment extends Fragment {
    private static final String descripIDKey = "calcID";

    Descriptor descriptor;

    void setDescripID(int descripID) {
        descriptor = Descriptors.getDescrip(descripID);
    }

//	@Override
//	public void onAttach(Context ctx) {
//		super.onAttach(ctx);
//		Log.v(Calc360.TAG, getClass().getSimpleName() + ".onAttach()");
//	}

    @Override
    public void onCreate(Bundle savedInstState) {
        super.onCreate(savedInstState);
//		Log.v(Calc360.TAG, getClass().getSimpleName() + ".onCreate(" +
// (savedInstState == null ? "null" : savedInstState.size()) + ")");
        if (savedInstState != null) {
            setDescripID(savedInstState.getInt(descripIDKey));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstState) {
        super.onCreateView(inflater, container, savedInstState);
//		Log.v(Calc360.TAG, getClass().getSimpleName() + ".onCreateView(" +
// (savedInstState == null ? "null" : savedInstState.size()) + ")");
        View view;
        try {
            view = createView(inflater, container, savedInstState);
            Activity act = getActivity();
            SharedPreferences prefs = act.getPreferences(Context.MODE_PRIVATE);
            restorePrefs(prefs);
        }
        catch (Exception ex) {
            view = inflater.inflate(R.layout.mistake, container, false);
        }
        return view;
    }

    protected abstract View createView(LayoutInflater inflater,
                                       ViewGroup container, Bundle savedInstState);

    protected void restorePrefs(SharedPreferences prefs) {
    }

//	@Override
//	public void onActivityCreated(Bundle savedInstState) {
//		Log.v(Calc360.TAG, getClass().getSimpleName() + ".onActivityCreated("
// + (savedInstState == null ? "null" : savedInstState.size()) + ")");
//		super.onActivityCreated(savedInstState);
//	}
//
//	@Override
//	public void onViewStateRestored(Bundle savedInstState) {
//		super.onViewStateRestored(savedInstState);
//		Log.v(Calc360.TAG, getClass().getSimpleName() + ".onViewStateRestored
// (" + (savedInstState == null ? "null" : savedInstState.size()) + ")");
//	}
//
//	@Override
//	public void onStart() {
//		super.onStart();
//		Log.v(Calc360.TAG, getClass().getSimpleName() + ".onStart()");
//	}

    @Override
    public void onResume() {
        super.onResume();
//		Log.v(Calc360.TAG, getClass().getSimpleName() + ".onResume()");
        try {
            Activity act = getActivity();
            act.setTitle(descriptor.getTitle(getResources()));
            View focused = act.getCurrentFocus();
            if (focused instanceof EditText) {
                InputMethodManager imm = (InputMethodManager)act
                        .getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(focused, InputMethodManager.SHOW_IMPLICIT);
            }
        }
        catch (Exception ex) {
        }
    }


//	@Override
//	public void onPause() {
//		super.onPause();
//		Log.v(Calc360.TAG, getClass().getSimpleName() + ".onPause()");
//	}

    @Override
    public void onSaveInstanceState(@NotNull Bundle savedInstState) {
        super.onSaveInstanceState(savedInstState);
//		Log.v(Calc360.TAG, getClass().getSimpleName() + ".onSaveInstanceState
// (" + savedInstState.size() + ")");
        savedInstState.putInt(descripIDKey, descriptor.getID());
    }


    // When this calculator is stopped by the Android system, save
    // the units chosen by the user into the preferences file.
    @Override
    public void onStop() {
//		Log.v(Calc360.TAG, getClass().getSimpleName() + ".onStop()");
        try {
            // Open the Android system preferences file for Calc360.
            SharedPreferences prefs = getActivity().getPreferences(
                    Context.MODE_PRIVATE);

            // Get an editor for the preferences files
            // so that we can write values into that file.
            SharedPreferences.Editor editor = prefs.edit();

            // Call savePrefs which will be
            // overridden in the individual calculators.
            savePrefs(editor);

            // Make the changes permanent.
            editor.apply();
        }
        catch (Exception ex) {
        }
        finally {
            super.onStop();
        }
    }

    protected void savePrefs(SharedPreferences.Editor editor) {
    }


//	@Override
//	public void onDestroyView() {
//		super.onDestroyView();
//		Log.v(Calc360.TAG, getClass().getSimpleName() + ".onDestroyView()");
//	}
//
//	@Override
//	public void onDestroy() {
//		super.onDestroy();
//		Log.v(Calc360.TAG, getClass().getSimpleName() + ".onDestroy()");
//	}
//
//	@Override
//	public void onDetach() {
//		super.onDetach();
//		Log.v(Calc360.TAG, getClass().getSimpleName() + ".onDetach()");
//	}
}

