package com.zizi.rendezvous.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.zizi.rendezvous.GlobalApp;
import com.zizi.rendezvous.Models.ModelSingleMeeting;
import com.zizi.rendezvous.R;

/** Фрагмент с функциями модерации */
public class FragmentModeration extends Fragment {

    private GlobalApp globalApp; //глобальный класс приложения, общий для всех компонентов

    public FragmentModeration() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_moderation, container, false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //инициализация
        globalApp = (GlobalApp) getActivity().getApplicationContext();

        //ищем вьюхи
        MaterialToolbar materialToolbar = getActivity().findViewById(R.id.material_toolbar);
        ; // верхняя панелька

        // materialToolbar //////////////////////////////////////////////////////////////////////////
        materialToolbar.setTitle("Модерация"); // заголовок чата
        //materialToolbar.getMenu().findItem(R.id.request).setVisible(false); // скрываем пункт заявки на встречу
        materialToolbar.setNavigationIcon(R.drawable.ic_outline_arrow_back_24); // делаем кнопку навигации стрелкой


        // событие при клике на кнопку навигации, на этом фрагменте она в виде стрелочки
        materialToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed(); // перегружаем кнопку на такое же действие, как у кнопки назад
            }
        });
        //===========================================================================================

    }

}