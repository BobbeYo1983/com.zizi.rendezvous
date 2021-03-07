package com.zizi.rendezvous.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.ScrollView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.zizi.rendezvous.Activity.ActivityLogin;
import com.zizi.rendezvous.GlobalApp;
import com.zizi.rendezvous.Models.ModelMeeting;
import com.zizi.rendezvous.R;

import java.util.Random;

/** Фрагмент с функциями модерации */
public class FragmentModeration extends Fragment {

    private GlobalApp globalApp; //глобальный класс приложения, общий для всех компонентов
    private ModelMeeting randomMeeting; //рандомная заявка из всех непрошедших модерацию

    //виджеты
    private TextInputLayout til_name;
    private TextInputEditText til_name_et; // имя пользователя
    private TextInputLayout til_age;
    private TextInputEditText til_age_et;
    private TextInputLayout til_phone;
    private TextInputEditText til_phone_et;
    private CheckBox cb_only_write; //галка можно ли звонить
    private TextInputLayout til_soc_net;
    private TextInputEditText til_soc_net_et;
    private TextInputLayout til_contact;
    private TextInputEditText til_contact_et;
    private TextInputLayout til_region;
    private TextInputEditText til_region_et;
    private TextInputLayout til_town;
    private TextInputEditText til_town_et;
    private TextInputLayout til_place;
    private TextInputEditText til_place_et;
    private TextInputLayout til_time;
    private TextInputEditText til_time_et;
    private TextInputLayout til_comment;
    private TextInputEditText til_comment_et;
    private Button bReject;
    private Button bAccept;
    private ProgressBar progressBar;
    private ScrollView scrollView;

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

        til_name = getActivity().findViewById(R.id.til_name);
        til_name_et = getActivity().findViewById(R.id.til_name_et);
        til_age = getActivity().findViewById(R.id.til_age);
        til_age_et = getActivity().findViewById(R.id.til_age_et);
        til_phone = getActivity().findViewById(R.id.til_phone);
        til_phone_et = getActivity().findViewById(R.id.til_phone_et);
        cb_only_write = getActivity().findViewById(R.id.cb_only_write);
        til_soc_net = getActivity().findViewById(R.id.til_soc_net);
        til_soc_net_et = getActivity().findViewById(R.id.til_soc_net_et);
        til_contact = getActivity().findViewById(R.id.til_contact);
        til_contact_et = getActivity().findViewById(R.id.til_contact_et);
        til_region = getActivity().findViewById(R.id.til_region);
        til_region_et = getActivity().findViewById(R.id.til_region_et);
        til_town = getActivity().findViewById(R.id.til_town);
        til_town_et = getActivity().findViewById(R.id.til_town_et);
        til_place = getActivity().findViewById(R.id.til_place);
        til_place_et = getActivity().findViewById(R.id.til_place_et);
        til_time = getActivity().findViewById(R.id.til_time);
        til_time_et = getActivity().findViewById(R.id.til_time_et);
        til_comment = getActivity().findViewById(R.id.til_comment);
        til_comment_et = getActivity().findViewById(R.id.til_comment_et);
        bReject = getActivity().findViewById(R.id.bReject);
        bAccept = getActivity().findViewById(R.id.bAccept);
        progressBar = getActivity().findViewById(R.id.progressBar);
        scrollView = getActivity().findViewById(R.id.scrollViewFragmentModeration);

        //ShowProgressBar(true);

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



        //bReject///////////////////////////////////////////////////////////////////////////////////
        bReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReadRandomMeeting(); //читаем одну случайную неморерированую заявку
            }
        });
        //==========================================================================================



        //bAccept///////////////////////////////////////////////////////////////////////////////////
        bAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                randomMeeting.setModeration(true); //отмечаем, что модерация пройдена
                DocumentReference documentReference = globalApp.GenerateDocumentReference("meetings", randomMeeting.getUserID()); //ссылка на заявку текущего пользователя
                documentReference.set(randomMeeting).addOnCompleteListener(new OnCompleteListener<Void>() { //записываем в БД
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) { //если запись успешна

                            ReadRandomMeeting(); //читаем следующую случайную неморерированую заявку

                        } else { //если запись не

                            globalApp.Log(getClass().getSimpleName(), "bAccept.setOnClickListener",
                                    "Ошибка записи отметки об успешной модерации в БД: " + task.getException(), true);

                            //Покажем пользователю сообщение/////////////////////////////
                            new AlertDialog.Builder(getContext())
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .setTitle("Ошибка записи в БД")
                                    .setMessage("Ошибка записи отметки об успешной модерации в БД, повторите попытку еще раз.")
                                    .setPositiveButton("Понятно", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                        }
                                    })
                                    //.setNegativeButton("No", null)
                                    .show();
                            //===============================================================================
                        }

                    }
                });


            }
        });
        //==========================================================================================

    }

    @Override
    public void onStart() {
        super.onStart();

        if (!globalApp.IsAuthorized()) { // если пользователь не авторизован
            startActivity(new Intent(getActivity().getApplicationContext(), ActivityLogin.class)); // отправляем к началу на авторизацию
            getActivity().finish(); // убиваем активити
        }

        ReadRandomMeeting(); //читаем одну случайную неморерированую заявку

    }



    /** Читаем из базы одну случайную заявку не прошедшую случайную модерацию.
     * В БД лежат документы со случайным числом в поле meetingId.
     * Генерируем случайное число и берем первое встречающееся перед этим числом.
     * */
    private void ReadRandomMeeting() {

        ShowProgressBar(true);

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

                    if (!task.getResult().getDocuments().isEmpty()){ //если хотя бы один документ вычитан

                        DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0); //получаем документ фаресторе

                        randomMeeting = documentSnapshot.toObject(ModelMeeting.class); //сериализация в класс

                        UpdateUI(randomMeeting); //обновляем интерфейс пользователя

                    } else { //если нет ни одного документа на модерацию

                        progressBar.setVisibility(View.INVISIBLE);
                        scrollView.setVisibility(View.INVISIBLE);

                        //Покажем пользователю и разрешаем повторить чтение/////////////////////////////
                        new AlertDialog.Builder(getContext())
                                .setIcon(android.R.drawable.ic_dialog_info)
                                .setTitle("Нет данных")
                                .setMessage("Нет заявок на модерацию.")
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
    }


    /**
     * Обновляет данные интерфейса пользователя
     * @param modelMeeting заявка на встречу
     */
    private void UpdateUI(ModelMeeting modelMeeting) {

        til_name_et.setText(modelMeeting.getName());
        til_age_et.setText(modelMeeting.getAge());
        til_phone_et.setText(modelMeeting.getPhone());

        if (modelMeeting.getOnlyWrite().equals("trueTrue")) {
            cb_only_write.setChecked(true);
        } else {
            cb_only_write.setChecked(false);
        }

        til_soc_net_et.setText(modelMeeting.getSocNet());
        Linkify.addLinks(til_soc_net_et, Linkify.ALL); // для распознования ссылок
        til_soc_net_et.setLinkTextColor(Color.BLUE);

        til_contact_et.setText(modelMeeting.getContact());
        til_region_et.setText(modelMeeting.getRegion());
        til_town_et.setText(modelMeeting.getTown());
        til_place_et.setText(modelMeeting.CreateStringFromArrayListPlaces());
        til_time_et.setText(modelMeeting.getTime());
        til_comment_et.setText(modelMeeting.getComment());

        ShowProgressBar(false);

    }


    /**
     * Показывает или прогрессбар или виджеты
     * @param visibility если true, то показывает прогрессбар
     */
    private void ShowProgressBar(boolean visibility) {
        if (visibility) {
            progressBar.setVisibility(View.VISIBLE);
            scrollView.setVisibility(View.GONE);
        } else {
            progressBar.setVisibility(View.GONE);
            scrollView.setVisibility(View.VISIBLE);

        }
    }


}