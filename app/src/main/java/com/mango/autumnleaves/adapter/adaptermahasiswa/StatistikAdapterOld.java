//package com.mango.autumnleaves.adapter.adaptermahasiswa;
//
//import android.content.Context;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ProgressBar;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.firestore.DocumentReference;
//import com.google.firebase.firestore.DocumentSnapshot;
//import com.google.firebase.firestore.FirebaseFirestore;
//import com.google.firebase.firestore.QueryDocumentSnapshot;
//import com.google.firebase.firestore.QuerySnapshot;
//import com.mango.autumnleaves.R;
//import com.mango.autumnleaves.model.Statistik;
//import com.mango.autumnleaves.model.mahasiswa.UserMahasiswa;
//import com.mango.autumnleaves.util.Constant;
//
//
//import java.util.List;
//
//public class StatistikAdapterOld extends RecyclerView.Adapter<StatistikAdapterOld.ViewHolder> {
//
//    private Context mContext;
//    private List<Statistik> mData;
//    private FirebaseAuth firebaseAuth;
//    private FirebaseUser firebaseUser;
//    private FirebaseFirestore firebaseFirestore;
//    private ProgressBar progressBar;
//
//
//    public StatistikAdapterOld(Context mContext, List<Statistik> mData) {
//        this.mContext = mContext;
//        this.mData = mData;
//    }
//
//    @NonNull
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view;
//        LayoutInflater inflater = LayoutInflater.from(mContext);
//        view = inflater.inflate(R.layout.list_item_statistik, parent, false);
//        return new StatistikAdapterOld.ViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//
//        firebaseAuth = FirebaseAuth.getInstance();
//        firebaseFirestore = FirebaseFirestore.getInstance();
//        firebaseUser = firebaseAuth.getCurrentUser();
//
//        holder.Matakuliah.setText(mData.get(position).getMatakuliah());
//        holder.Dosen.setText(mData.get(position).getDosen());
//        holder.Pertemuan.setText("Hadir :" + " " +
//                String.valueOf(mData.get(position).getPertemuan())
//                + " dari : " +
//                String.valueOf(mData.get(position).getJumlah_pertemuan()));
//
//
//
//        DocumentReference docRef = firebaseFirestore.collection("user").document(firebaseAuth.getUid());
//        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                if (task.isSuccessful()) {
//                    DocumentSnapshot document = task.getResult();
//                    if (document.exists()) {
//                        UserMahasiswa userMahasiswa = new UserMahasiswa();
//                        userMahasiswa.setNama(document.getString("nama"));
//                        userMahasiswa.setJurusan(document.getString("jurusan"));
//                        userMahasiswa.setKode_kelas(document.getString("kode_kelas"));
//                        // Doc Ref Dari user
//                        String jurusanRef = userMahasiswa.getJurusan();
//                        String kelasRef = userMahasiswa.getKode_kelas();
//
//                        firebaseFirestore
//                                .collection("statistik")
//                                .document("kelas")
//                                .collection(kelasRef)
//                                .document(firebaseAuth.getUid())
//                                .collection("jadwal")
//                                .get()
//                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                                    @Override
//                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                                        if (task.isSuccessful()) {
//                                            for (QueryDocumentSnapshot querySnapshot : task.getResult()){
//                                                Statistik statistik = new Statistik();
//                                                statistik.setPertemuan(querySnapshot.getLong("pertemuan").intValue());
//                                                statistik.setJumlah_pertemuan(querySnapshot.getLong("jumlah_pertemuan").intValue());
//
//                                                float tPertemuan = mData.get(position).getPertemuan();
//                                                float JumPertemuan = mData.get(position).getJumlah_pertemuan();
//                                                float persen = (tPertemuan / JumPertemuan) * 100;
//                                                holder.Persentase.setText(" | Persentase : " + String.valueOf(persen) + "%");
//                                                holder.progressBar.setProgress((int) persen);
////                                            }
//                                        }
//                                    }
//                                });
//
//                    } else {
//                        Log.d("TAG", "Documment tidak ada");
//                    }
//                } else {
//                    Log.d("TAG", "gagal", task.getException());
//                }
//            }
//        });
//    }
//
//    @Override
//    public int getItemCount() {
//        return mData.size();
//    }
//
//    public class ViewHolder extends RecyclerView.ViewHolder {
//
//        private TextView Matakuliah;
//        private TextView Dosen;
//        private TextView Pertemuan;
//        private TextView Persentase;
//        private ProgressBar progressBar;
//
//        public ViewHolder(@NonNull View itemView) {
//            super(itemView);
//
//            Matakuliah = itemView.findViewById(R.id.statMatakuliah);
//            Dosen = itemView.findViewById(R.id.statDosen);
//            Pertemuan = itemView.findViewById(R.id.statPertemuan);
//            Persentase = itemView.findViewById(R.id.statPersentase);
//            progressBar = itemView.findViewById(R.id.ststProgressBar);
//        }
//    }
//}
