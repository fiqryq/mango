package com.mango.autumnleaves.adapter.adapterdosen;

public class DetailBapBackup {
//    public class DetailBapAdapter extends RecyclerView.Adapter<com.mango.autumnleaves.adapter.adapterdosen.DetailBapAdapter.ViewHolder> {
//
//        private Context mContext;
//        private List<DetailBap> mData;
//        private String mIdDosen;
//        private String mIdBap;
//        private String mIdMatkul ="";
//        private String mKelasMatkul ="";
//        private static int Post = 3000;
//
//        public DetailBapAdapter(Context mContext, List<DetailBap> mData, String mIdDosen, String mIdBap) {
//            this.mContext = mContext;
//            this.mData = mData;
//            this.mIdDosen = mIdDosen;
//            this.mIdBap = mIdBap;
//        }
//
//        public DetailBapAdapter() {
//
//        }
//
//        DocumentReference reference;
//        Map<String, Object> dataBap;
//        Map<String, Object> idMahasiswa;
//
//        @NonNull
//        @Override
//        public com.mango.autumnleaves.adapter.adapterdosen.DetailBapAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//            View view;
//            LayoutInflater inflater = LayoutInflater.from(mContext);
//            view = inflater.inflate(R.layout.list_mahasiswa_bap, parent, false);
//            return new com.mango.autumnleaves.adapter.adapterdosen.DetailBapAdapter.ViewHolder(view);
//        }
//
//        @Override
//        public void onBindViewHolder(@NonNull com.mango.autumnleaves.adapter.adapterdosen.DetailBapAdapter.ViewHolder holder, int position) {
//            Log.d("CHECK_PARAMETER", mData.get(position).getIdMahasiswa());
//            int ai = position + 1;
//            holder.tvNo.setText(String.valueOf(ai));
//            holder.tvNamaMahasiswa.setText(mData.get(position).getName());
//
//            DocumentReference documentReference = FirebaseFirestore.getInstance()
//                    .collection("user").document(mIdDosen);
//            documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                @Override
//                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                    if (task.isSuccessful()) {
//                        DocumentSnapshot document = task.getResult();
//                        if (document.exists()) {
//
//                            UserDosen userDosen = new UserDosen();
//                            userDosen.setNip(document.getString("nip"));
//                            // Doc Ref Dari user
//                            String nipRef = userDosen.getNip();
//
//                            FirebaseFirestore.getInstance()
//                                    .collection("jadwalDosen")
//                                    .document(nipRef)
//                                    .collection("jadwal")
//                                    .whereEqualTo("hari", getNameDay())
//                                    .whereLessThan("waktu_mulai", getHour())
//                                    .orderBy("waktu_mulai", Query.Direction.DESCENDING)
//                                    .limit(1)
//                                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                                @Override
//                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                                    if (task.isSuccessful()) {
//                                        for (QueryDocumentSnapshot document : task.getResult()) {
//                                            Jadwal jadwal = new Jadwal();
//                                            jadwal.setId(document.getString("id"));
//                                            jadwal.setKelas(document.getString("kelas"));
//                                            mIdMatkul = jadwal.getId();
//                                        }
//                                    }
//                                }
//                            });
//                        }
//                    }
//                }
//            });
//
//
//            reference = FirebaseFirestore.getInstance().collection("dosen").document(mIdDosen).collection("bap").document(mIdBap);
//            dataBap = new HashMap<>();
//            idMahasiswa = new HashMap<>();
//
//            int status = mData.get(position).getStatus();
//
//            if (status == 1) {
//                //hadir
//                holder.radioHadir.setChecked(true);
//            } else if (status == 2) {
//                //izin
//                holder.radioIzin.setChecked(true);
//            } else if (status == 3) {
//                //sakit
//                holder.radioSakit.setChecked(true);
//            } else {
//                //alfa
//                holder.radioAlfa.setChecked(true);
//            }
//
//            String nama = mData.get(position).getName();
//            idMahasiswa.put(mData.get(position).getIdMahasiswa(), Arrays.asList(nama, 0));
//
//            holder.radioSakit.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    idMahasiswa.put(mData.get(position).getIdMahasiswa(), Arrays.asList(nama, 3));
//                    dataBap.put("mahasiswa", idMahasiswa);
//
//                    reference.update(dataBap).addOnSuccessListener(new OnSuccessListener<Void>() {
//                        @Override
//                        public void onSuccess(Void aVoid) {
//                            Toast.makeText(mContext, "Update Berhasil", Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                }
//            });
//
//            holder.radioAlfa.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    idMahasiswa.put(mData.get(position).getIdMahasiswa(), Arrays.asList(nama, 0));
//                    dataBap.put("mahasiswa", idMahasiswa);
//
//                    reference.update(dataBap).addOnSuccessListener(new OnSuccessListener<Void>() {
//                        @Override
//                        public void onSuccess(Void aVoid) {
//                            Toast.makeText(mContext, "Update Berhasil", Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                }
//            });
//
//            holder.radioIzin.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    idMahasiswa.put(mData.get(position).getIdMahasiswa(), Arrays.asList(nama, 2));
//                    dataBap.put("mahasiswa", idMahasiswa);
//
//                    reference.update(dataBap).addOnSuccessListener(new OnSuccessListener<Void>() {
//                        @Override
//                        public void onSuccess(Void aVoid) {
//                            Toast.makeText(mContext, "Update Berhasil", Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                }
//            });
//
//            holder.radioHadir.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    idMahasiswa.put(mData.get(position).getIdMahasiswa(), Arrays.asList(nama, 1));
//                    dataBap.put("mahasiswa", idMahasiswa);
//
//                    reference.update(dataBap).addOnSuccessListener(new OnSuccessListener<Void>() {
//                        @Override
//                        public void onSuccess(Void aVoid) {
//                            Toast.makeText(mContext, "Update Berhasil", Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                }
//            });
//
//
//
//
//        }
//
//        @Override
//        public int getItemCount() {
//            return mData.size();
//        }
//
//        public void updateData() {
//
//        }
//
//        class ViewHolder extends RecyclerView.ViewHolder {
//            final TextView tvNo;
//            final TextView tvNamaMahasiswa;
//            final RadioGroup radioKehadiran;
//            final RadioButton radioHadir, radioSakit, radioIzin, radioAlfa;
//
//            public ViewHolder(@NonNull View itemView) {
//                super(itemView);
//
//                tvNo = itemView.findViewById(R.id.tvNomor_bap);
//                tvNamaMahasiswa = itemView.findViewById(R.id.tvNamaMahasiswa_bap);
//                radioKehadiran = itemView.findViewById(R.id.radio_kehadiran_bap);
//                radioHadir = itemView.findViewById(R.id.radio_hadir_bap);
//                radioSakit = itemView.findViewById(R.id.radio_sakit_bap);
//                radioIzin = itemView.findViewById(R.id.radio_izin_bap);
//                radioAlfa = itemView.findViewById(R.id.radio_alfa_bap);
//            }
//        }
//    }
}
