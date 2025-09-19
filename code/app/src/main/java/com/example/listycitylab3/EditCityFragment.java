package com.example.listycitylab3;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import androidx.appcompat.app.AlertDialog;

public class EditCityFragment extends DialogFragment {

    public interface EditCityDialogListener {
        void editCity(City city);
    }

    private EditCityDialogListener listener;
    private City city;

    // empty constructor (apparently its needed?)
    public EditCityFragment() {}

    public static EditCityFragment newInstance(City city) {
        Bundle args = new Bundle();
        args.putSerializable("city", city);
        EditCityFragment fragment = new EditCityFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof EditCityDialogListener) {
            listener = (EditCityDialogListener) context;
        } else {
            throw new RuntimeException(context + " must implement EditCityDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        if (getArguments() != null) {
            city = (City) getArguments().getSerializable("city");
        }

        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_add_city, null);
        EditText editCityName = view.findViewById(R.id.edit_text_city_text);
        EditText editProvinceName = view.findViewById(R.id.edit_text_province_text);

        if (city != null) {
            editCityName.setText(city.getName());
            editProvinceName.setText(city.getProvince());
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setView(view)
                .setTitle("Edit city")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Save", (dialog, which) -> {
                    String newName = editCityName.getText().toString().trim();
                    String newProvince = editProvinceName.getText().toString().trim();

                    if (city != null) {
                        city.setName(newName);
                        city.setProvince(newProvince);
                        listener.editCity(city);
                    }
                })
                .create();
    }
}
