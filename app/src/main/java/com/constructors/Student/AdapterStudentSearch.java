package com.constructors.Student;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.constructors.Model.TutorModel;
import com.constructors.RegisterLogin.MainActivity;
import com.constructors.findtutor.R;


import java.util.ArrayList;

public class AdapterStudentSearch extends RecyclerView.Adapter<AdapterStudentSearch.MyViewHolder> {
    Context context;
    ArrayList<String> tutorInfo;
    ArrayList<String> nameTutor;
    ArrayList<String> listAddress;
    ArrayList<String> listQualification;
    ArrayList<String> listExperience;
    ArrayList<String> listUid;

    public static String getUid;


    public AdapterStudentSearch(Context c , ArrayList<String> p, ArrayList<String> q, ArrayList<String> a , ArrayList<String> qual , ArrayList<String> e, ArrayList<String> u)
    {
        context = c;
        tutorInfo = p;
        nameTutor = q;
        listAddress = a;
        listQualification = qual;
        listExperience = e;
        listUid = u;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.recycler_view_layout_student_search,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {



        holder.contact.setText(tutorInfo.get(position));
        holder.name.setText(nameTutor.get(position));
        holder.address.setText(listAddress.get(position));
        holder.qualification.setText(listQualification.get(position));
        holder.experience.setText(listExperience.get(position));


        holder.showTutorProf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context , ShowTutorFullProfileToStudent.class);
//                intent.putExtra("Title", listUid.get(position).toString());
                getUid = listUid.get(position);

                context.startActivity(intent);


            }
        });


//        holder.address.setText(tutorInfo.get(position).gettutAddress());
//        holder.qualification.setText(tutorInfo.get(position).getutQualification());
//        holder.experience.setText(tutorInfo.get(position).gettutExperience());



    }

    @Override
    public int getItemCount() {
        return tutorInfo.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView contact,name,address,qualification,experience, uid;
        Button showTutorProf;


        public MyViewHolder(View itemView) {
            super(itemView);
            contact = (TextView) itemView.findViewById(R.id.contactRV);
            name = (TextView) itemView.findViewById(R.id.nameRV);
            address = (TextView) itemView.findViewById(R.id.addressRV);
            qualification = (TextView) itemView.findViewById(R.id.qualificationRV);
            experience = (TextView) itemView.findViewById(R.id.experienceRV);
            uid = (TextView) itemView.findViewById(R.id.uidRV);

            showTutorProf = (Button) itemView.findViewById(R.id.showTutorProfile);


        }
    }
}
