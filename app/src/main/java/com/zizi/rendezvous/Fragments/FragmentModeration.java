package com.zizi.rendezvous.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.zizi.rendezvous.Activity.ActivityLogin;
import com.zizi.rendezvous.GlobalApp;
import com.zizi.rendezvous.Models.ModelSingleMeeting;
import com.zizi.rendezvous.R;

import java.util.Random;

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
        MaterialToolbar materialToolbar = getActivity().findViewById(R.id.material_toolbar); // верхняя панелька

        // materialToolbar //////////////////////////////////////////////////////////////////////////
        materialToolbar.setTitle("Модерация"); // заголовок чата
        materialToolbar.setNavigationIcon(R.drawable.ic_outline_arrow_back_24); // делаем кнопку навигации стрелкой
        //==========================================================================================


        // событие при клике на кнопку навигации, на этом фрагменте она в виде стрелочки
        materialToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed(); // перегружаем кнопку на такое же действие, как у кнопки назад
            }
        });
        //===========================================================================================

    }

    @Override
    public void onStart() {
        super.onStart();

        if (!globalApp.IsAuthorized()) { // если пользователь не авторизован
            startActivity(new Intent(getActivity().getApplicationContext(), ActivityLogin.class)); // отправляем к началу на авторизацию
            getActivity().finish(); // убиваем активити
        }

        //TODO сделать прогрессбар

        ReadRandomMeeting(); //читаем одну случайную неморерированую заявку

    }



    /** Читаем из базы одну случайную заявку не прошедшую случайную модерацию.
     * В БД лежат документы со случайным числом в поле meetingId.
     * Генерируем случайное число и берем первое встречающееся перед этим числом.
     * */
    private void ReadRandomMeeting() {

        Random random = new Random(); // случайное число

        CollectionReference collectionReference;
        collectionReference = globalApp.GenerateCollectionReference("meetings");

        //запрос в БД
        Query query = collectionReference
                        //.whereGreaterThanOrEqualTo("meetingID", random.nextLong())
                        .whereGreaterThan("meetingID", random.nextLong()) //выбираем заявку со следующим ID после случайного числа
                        .orderBy("meetingID") // упорядочиваем по ID
                        .limit(1) // читаем только первую
                        ;

        //получаем данные из БД
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()) { //если чтение из БД успешно

                    globalApp.Log(getClass().getSimpleName(), "ReadRandomMeeting/onComplete",
                            "Чтение случайной заявки из БД выполнено успешно", false);

                    if (task.getResult().getDocuments().get(0).exists()){ //если хотя бы один документ вычитан

                        DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0); //получаем документ фаресторе

                        //TODO обновить значения полей из документа

                    } else { //если нет ни одного документа на модерацию

                        //Покажем пользователю и разрешаем повторить чтение/////////////////////////////
                        new AlertDialog.Builder(getContext())
                                .setIcon(android.R.drawable.ic_dialog_info)
                                .setTitle("Нет данных")
                                .setMessage("Нет заявок на модерацию")
                                .setPositiveButton("Повторить", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        ReadRandomMeeting();
                                    }
                                })
                                //.setNegativeButton("No", null)
                                .show();
                        //===============================================================================
                    }

                    //for (QueryDocumentSnapshot document : task.getResult()) {
                    //    Log.d(TAG, document.getId() + " => " + document.getData());
                    //}

                } else { //если чтение из БД не успешно

                    globalApp.Log(getClass().getSimpleName(), "ReadRandomMeeting/onComplete",
                            "Ошибка чтения случайной заявки из БД: " + task.getException(), true);

                    //Покажем пользователю и разрешаем повторить чтение/////////////////////////////
                    new AlertDialog.Builder(getContext())
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setTitle("Ошибка чтения БД")
                            .setMessage("Ошибка чтения случайной заявки из БД: " + task.getException())
                            .setPositiveButton("Повторить", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    ReadRandomMeeting();
                                }
                            })
                            //.setNegativeButton("No", null)
                            .show();
                    //===============================================================================

                }

            }
        });




/*        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() { // вешаем слушателя на задачу чтения документа из БД
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) { // как задача чтения выполнилась
                if (task.isSuccessful()) { // если выполнилась успешно
                    DocumentSnapshot document = task.getResult(); // получаем документ
                    if (document.exists()) { // если документ такой есть, не null
                        ModelSingleMeeting requestMeetingPartner = document.toObject(ModelSingleMeeting.class); // получаем заявку текущего пользователя из БД
                        UpdateUI(requestMeetingPartner); // обновляем данные в полях
                    } else { // если документа не существует

                        globalApp.Log("FragmentDetailsMeeting", "onStart/onComplete", "Запрошенного документа нет в БД", true);
                    }

                } else { // если ошибка чтения БД

                    globalApp.Log ("FragmentDetailsMeeting", "onStart/onComplete", "Ошибка чтения БД: " + task.getException(), true);
                }
            }
        });*/


    }


    
}