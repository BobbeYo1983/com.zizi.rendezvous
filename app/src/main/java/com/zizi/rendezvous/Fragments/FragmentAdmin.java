package com.zizi.rendezvous.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.zizi.rendezvous.BuildConfig;
import com.zizi.rendezvous.GlobalApp;
import com.zizi.rendezvous.R;

import java.sql.Struct;

/** Фрагмент с функциями администратора */
public class FragmentAdmin extends Fragment {

    private GlobalApp globalApp; // глобальный класс приложения

    private MaterialToolbar materialToolbar; // верхняя панелька
    private ProgressBar progressBar; //крутилка
    private TextInputEditText tietCountMeetings; //количество активных заявок на встречи
    private boolean tietCountMeetingsIsUpdate; // поле обновлено/вычитано из БД




    public FragmentAdmin() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_admin, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        globalApp = (GlobalApp) getActivity().getApplicationContext(); //получаем глобальный класс приложения
        tietCountMeetingsIsUpdate = false; //отмечаем, что поле не обновлено

        //ищем вьюхи
        materialToolbar = getActivity().findViewById(R.id.material_toolbar); // верхняя панелька
        progressBar = getActivity().findViewById(R.id.progressBar);
        tietCountMeetings = getActivity().findViewById(R.id.tietCountMeetings);

    }

    @Override
    public void onStart() {
        super.onStart();

        // materialToolbar /////////////////////////////////////////////////////////////////////////
        materialToolbar.setTitle(globalApp.GetBundle("title")); //устанавливаем название из аргументов
        materialToolbar.getMenu().findItem(R.id.request).setVisible(false); // скрываем пункт заявки на встречу
        //==========================================================================================

        ShowProgressBar(true);//показать экран ожидания

        //Запрос количества заявок
        CollectionReference collectionReference = globalApp.GenerateCollectionReference("meetings");
        collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    globalApp.Log(getClass().getSimpleName(), "onStart/onComplete", "Количество заявок на встречу = " + task.getResult().size(), false);
                    int countMeetings = task.getResult().size(); //количество встреч
                    tietCountMeetings.setText(String.valueOf(countMeetings));
                    tietCountMeetingsIsUpdate = true; //отмечаем, что поле обновлено
                    UpdateUI(); //пробуем обновить интерфейс, сейчас там крутится прогрессбар
                } else {
                    globalApp.Log(getClass().getSimpleName(), "onStart/onComplete", "Ошибка при запросе количества заявок на встречу: " + task.getException(), false);
                }
            }
        });

    }



    /** Обновление и отображение всех виджетов, если все обновились и готовы к отображению*/
    private void UpdateUI() {

        //если все виджеты обновились, то показываем
        if (tietCountMeetingsIsUpdate) {

            ShowProgressBar(false);

            tietCountMeetingsIsUpdate = false; //сбрасываем признак обновления поля
        }else { //если не все виджеты обновились, то дальшее крутим баранку

            ShowProgressBar(true);

        }

    }



    /**
     * Показать экран ожидания.
     * @param show показать/не показать экран ожидания
     */
    private void ShowProgressBar (boolean show) {
        if (show) {
            progressBar.setVisibility(View.VISIBLE);

            tietCountMeetings.setVisibility(View.GONE);
        } else {
            progressBar.setVisibility(View.GONE);

            tietCountMeetings.setVisibility(View.VISIBLE);
        }

    }

}