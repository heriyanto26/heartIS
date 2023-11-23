package com.example.welcome;
import android.annotation.SuppressLint;
import android.os.Parcel;

import com.google.android.material.datepicker.CalendarConstraints;
import java.util.List;
import java.util.Calendar;

public class DatePickerUtils {

    public static CalendarConstraints.Builder disableSpecificDates(List<Calendar> disabledDates) {
        CalendarConstraints.Builder constraintsBuilder = new CalendarConstraints.Builder();
        CalendarConstraints.DateValidator validator = new CustomDateValidator(disabledDates);

        constraintsBuilder.setValidator(validator);
        return constraintsBuilder;
    }

    @SuppressLint("ParcelCreator")
    private static class CustomDateValidator implements CalendarConstraints.DateValidator {
        private final List<Calendar> disabledDates;

        public CustomDateValidator(List<Calendar> disabledDates) {
            this.disabledDates = disabledDates;
        }

        @Override
        public boolean isValid(long date) {
            Calendar selectedDate = Calendar.getInstance();
            selectedDate.setTimeInMillis(date);

            for (Calendar disabledDate : disabledDates) {
                // Compare year, month, and day of selectedDate with disabledDate
                if (selectedDate.get(Calendar.YEAR) == disabledDate.get(Calendar.YEAR) &&
                        selectedDate.get(Calendar.MONTH) == disabledDate.get(Calendar.MONTH) &&
                        selectedDate.get(Calendar.DAY_OF_MONTH) == disabledDate.get(Calendar.DAY_OF_MONTH)) {
                    return false;
                }
            }

            return true;


        }

        @Override
        public int describeContents() {
            return 0;
        }



        @Override
        public void writeToParcel(Parcel parcel, int i) {
            // No need to implement this for now
        }
    }
}
