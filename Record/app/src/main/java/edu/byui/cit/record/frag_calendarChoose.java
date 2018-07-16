package edu.byui.cit.record;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.Toast;

import java.util.Calendar;

import edu.byui.cit.widget.ButtonWrapper;
import edu.byui.cit.widget.CITFragment;
import edu.byui.cit.widget.ClickListener;
import edu.byui.cit.widget.DateWrapper;
import edu.byui.cit.widget.WidgetWrapper;

public class frag_calendarChoose extends CITFragment {

    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstState) {
        View view = inflater.inflate(R.layout.fragment_frag_calendar_choose, container, false);

        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));

        new ButtonWrapper(view, R.id.calendar_ok_button, new calendarClickListner());
        new DateWrapper(view, R.id.completionDate, calendar);

        return view;
    }

    @Override
    protected String getTitle() {
        return "Select Finish Date";
    }

    private class calendarClickListner implements ClickListener {
        @Override
        public void clicked(WidgetWrapper source) {
            DatePicker finishDate = getActivity().findViewById(R.id.completionDate);
            int day = finishDate.getDayOfMonth();
            int month = finishDate.getMonth() + 1;
            int year = finishDate.getYear();
            Toast toast = Toast.makeText(getActivity().getApplicationContext(), "read: " + month + "-" + day + "-" + year, Toast.LENGTH_LONG);
            toast.show();
            //TODO: somehow, return the completion date data to the previous screen (fragment)

            getActivity().getFragmentManager().popBackStack();
        }
    }
}
