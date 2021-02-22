package com.zizi.rendezvous.Fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputEditText;
import com.zizi.rendezvous.BuildConfig;
import com.zizi.rendezvous.GlobalApp;
import com.zizi.rendezvous.R;

/** Фрагмент с информацией о приложении */
public class FragmentAbout extends Fragment {

    private GlobalApp globalApp; // глобальный класс приложения

    private MaterialToolbar materialToolbar; // верхняя панелька
    private TextInputEditText tietVersion; //поле с отображением вресии

    public FragmentAbout() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_about, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        globalApp = (GlobalApp) getActivity().getApplicationContext(); //получаем глобальный класс приложения

        //ищем вьюхи
        materialToolbar = getActivity().findViewById(R.id.materialToolbar); // верхняя панелька
        tietVersion = getActivity().findViewById(R.id.tietVersion);



    }

    @Override
    public void onStart() {
        super.onStart();

        // materialToolbar /////////////////////////////////////////////////////////////////////////
        materialToolbar.setTitle(globalApp.GetBundle("title")); //устанавливаем название из аргументов
        materialToolbar.getMenu().findItem(R.id.request).setVisible(false); // скрываем пункт заявки на встречу
        //==========================================================================================

        String versionName = BuildConfig.VERSION_NAME; //получаем версию приложения
        tietVersion.setText(versionName); //устанавливаем версию

    }
}