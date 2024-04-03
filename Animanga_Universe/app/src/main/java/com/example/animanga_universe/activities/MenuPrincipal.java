package com.example.animanga_universe.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;

import com.example.animanga_universe.R;
import com.example.animanga_universe.clases.Usuario;
import com.example.animanga_universe.databinding.ActivityMenuPrincipalBinding;
import com.example.animanga_universe.fragments.BuscarFragment;
import com.example.animanga_universe.fragments.ForumsFragment;
import com.example.animanga_universe.fragments.HomeFragment;
import com.example.animanga_universe.fragments.ListasAnimeFragment;
import com.example.animanga_universe.fragments.ListasMangaFragment;
import com.example.animanga_universe.fragments.PerfilFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MenuPrincipal extends AppCompatActivity {
    Usuario user;
    ActivityMenuPrincipalBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user= getIntent().getParcelableExtra("usuario");
        binding= ActivityMenuPrincipalBinding.inflate(getLayoutInflater());
        binding.toolBar.setTitleTextAppearance(this, R.style.NarutoFont);
        setContentView(binding.getRoot());
        reemplazarFragment(new HomeFragment());
        binding.bottonNavigationView.setOnItemSelectedListener(item -> {
            int id= item.getItemId();
            if(id==R.id.ranking){
                binding.switchButton.setVisibility(View.GONE);
                reemplazarFragment(new HomeFragment());
            }else if(id== R.id.forums){
                binding.switchButton.setVisibility(View.GONE);
                reemplazarFragment(new ForumsFragment());

            }else if(id== R.id.buscar){
                binding.switchButton.setVisibility(View.GONE);
                reemplazarFragment(new BuscarFragment());

            } else if (id== R.id.listas) {
                binding.switchButton.setVisibility(View.VISIBLE);
                binding.switchButton.setChecked(false);

                reemplazarFragment(new ListasAnimeFragment());
                binding.switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @SuppressLint("UseCompatLoadingForDrawables")
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked){
                            binding.switchButton.setThumbIconDrawable(getDrawable(R.drawable.ic_m_foreground));
                            reemplazarFragment(new ListasMangaFragment());
                        }else{
                            binding.switchButton.setThumbIconDrawable(getDrawable(R.drawable.ic_a_foreground));
                            reemplazarFragment(new ListasAnimeFragment());
                        }
                    }
                });
            } else if (id==R.id.perfil) {
                binding.switchButton.setVisibility(View.GONE);
                reemplazarFragment(new PerfilFragment());

            }

            return true;
        });
        }
        public void reemplazarFragment(Fragment fragment){
            FragmentManager fragmentManager= getSupportFragmentManager();
            FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frame_layout, fragment);
            fragmentTransaction.commit();

        }

}